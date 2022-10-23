package com.imaec.alicorntest.ui.chat

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.alicorntest.model.ChatVo
import com.imaec.alicorntest.model.ChatVo.Companion.dtoToVo
import com.imaec.domain.model.ChatDto
import com.imaec.domain.repository.StompSocketListener
import com.imaec.domain.successOr
import com.imaec.domain.usecase.GetChatUseCase
import com.imaec.domain.usecase.SocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getChatUseCase: GetChatUseCase,
    private val socketUseCase: SocketUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ChatState>()
    val state: LiveData<ChatState> get() = _state

    private val _title = MutableLiveData(savedStateHandle.get<String>("name") ?: "")
    val title: LiveData<String> get() = _title

    private val _chatList = MutableLiveData<List<ChatVo>>()
    val chatList: LiveData<List<ChatVo>> get() = _chatList

    val message = MutableLiveData("")

    private val chatId = savedStateHandle.get<String>("chatId") ?: ""

    override fun onCleared() {
        socketUseCase.disconnect()
        super.onCleared()
    }

    private fun setChat(chat: List<ChatVo>?) {
        viewModelScope.launch(Dispatchers.Main) {
            _chatList.value = chat ?: chatList.value
            _state.value = ChatState.OnDataLoaded
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = ChatState.ShowToast(message)
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            setChat(getChatUseCase(chatId).successOr(emptyList()).map(::dtoToVo))
            socketUseCase.connect(
                "/chat/$chatId",
                object : StompSocketListener<ChatDto> {
                    override fun onMessage(message: ChatDto) {
                        setChat(chatList.value?.plus(dtoToVo(message)))
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
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun onClickSend() {
        if (message.value.isNullOrEmpty()) return
        socketUseCase.send(message.value ?: "")
        setChat(
            chatList.value?.plus(
                ChatVo(
                    message = message.value ?: "",
                    time = SimpleDateFormat("a h:mm").format(Date()),
                    isMy = true
                )
            )
        )
        message.value = ""
    }
}
