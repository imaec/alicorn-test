package com.imaec.alicorntest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.alicorntest.model.ChatListVo
import com.imaec.alicorntest.model.ChatListVo.Companion.dtoToVo
import com.imaec.domain.model.ChatListDto
import com.imaec.domain.repository.StompSocketListener
import com.imaec.domain.successOr
import com.imaec.domain.usecase.GetChatListUseCase
import com.imaec.domain.usecase.IsLoginUseCase
import com.imaec.domain.usecase.LogoutUseCase
import com.imaec.domain.usecase.SocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isLoginUseCase: IsLoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getChatListUseCase: GetChatListUseCase,
    private val socketUseCase: SocketUseCase
) : ViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> get() = _state

    private val _chatList = MutableLiveData<List<ChatListVo>>()
    val chatList: LiveData<List<ChatListVo>> get() = _chatList

    private fun setChatList(list: List<ChatListVo>?) {
        viewModelScope.launch(Dispatchers.Main) {
            _chatList.value = list ?: chatList.value
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = MainState.ShowToast(message)
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            if (isLoginUseCase().successOr(false)) {
                setChatList(getChatListUseCase("0").successOr(emptyList()).map(::dtoToVo))
                socketUseCase.connect(
                    "/chat/list/0",
                    object : StompSocketListener<ChatListDto> {
                        override fun onMessage(message: ChatListDto) {
                            // ?????? ?????? ??????
                            setChatList(
                                chatList.value?.map {
                                    if (it.chatId == message.chatId) {
                                        it.copy(
                                            time = message.time,
                                            message = message.message,
                                            unread = (it.unread.toInt() + 1).toString()
                                        )
                                    } else {
                                        it
                                    }
                                }
                            )
                        }

                        override fun onError(ex: Exception?) {
                            showToast(ex?.message ?: "")
                        }

                        override fun onEtc(message: String) {
                            showToast(message)
                        }

                        override fun onThrowable(t: Throwable?) {
                            showToast(t?.message ?: "")
                        }
                    }
                )
            } else {
                _state.value = MainState.StartLoginActivity
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            showToast("???????????? ???????????????.\n?????? ????????? ????????????.")
            fetchData()
        }
    }

    fun onClickChat(item: ChatListVo) {
        _state.value = MainState.OnClickChat(item)
    }
}
