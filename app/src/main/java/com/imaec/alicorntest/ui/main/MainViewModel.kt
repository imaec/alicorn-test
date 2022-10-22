package com.imaec.alicorntest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.alicorntest.model.ChatListVo
import com.imaec.alicorntest.model.ChatListVo.Companion.dtoToVo
import com.imaec.domain.successOr
import com.imaec.domain.usecase.GetChatListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> get() = _state

    private val _chatList = MutableLiveData<List<ChatListVo>>()
    val chatList: LiveData<List<ChatListVo>> get() = _chatList

    fun fetchData() {
        viewModelScope.launch {
            _chatList.value = getChatListUseCase("test").successOr(emptyList()).map(::dtoToVo)
        }
    }

    fun onClickChat(item: ChatListVo) {
        _state.value = MainState.OnClickChat(item)
    }
}
