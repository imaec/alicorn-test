package com.imaec.alicorntest.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.alicorntest.model.ChatVo
import com.imaec.alicorntest.model.ChatVo.Companion.dtoToVo
import com.imaec.domain.successOr
import com.imaec.domain.usecase.GetChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatUseCase: GetChatUseCase
) : ViewModel() {

    private val _state = MutableLiveData<ChatState>()
    val state: LiveData<ChatState> get() = _state

    private val _chatList = MutableLiveData<List<ChatVo>>()
    val chatList: LiveData<List<ChatVo>> get() = _chatList

    fun fetchData() {
        viewModelScope.launch {
            _chatList.value = getChatUseCase("test").successOr(emptyList()).map(::dtoToVo)
            _state.value = ChatState.OnDataLoaded
        }
    }
}
