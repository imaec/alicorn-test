package com.imaec.alicorntest.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaec.alicorntest.model.ChatVo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData<ChatState>()
    val state: LiveData<ChatState> get() = _state

    private val _chatList = MutableLiveData<List<ChatVo>>()
    val chatList: LiveData<List<ChatVo>> get() = _chatList

    fun fetchData() {
        _chatList.value = listOf(
            ChatVo(
                message = "안녕하세요.",
                time = "오전 10:23"
            ),
            ChatVo(
                message = "안녕하세요.",
                time = "오전 10:23",
                isMy = true
            ),
            ChatVo(
                message = "안녕하세요. 김이름님 잘지내셨나요? 일전에 UX세미나에서 반가웠습니다. 다름이 아니라 부탁드릴것이 있어서 이렇게 연락드립니다.",
                time = "오전 10:24"
            ),
            ChatVo(
                message = "아 죄송해요, 이제야 메세지를 봤습니다. 저도 반가웠습니다! 어떤 부탁인가요?",
                time = "오전 10:27",
                isMy = true
            ),
            ChatVo(
                message = "안녕하세요.",
                time = "오전 10:23"
            ),
            ChatVo(
                message = "안녕하세요.",
                time = "오전 10:23",
                isMy = true
            ),
            ChatVo(
                message = "안녕하세요. 김이름님 잘지내셨나요? 일전에 UX세미나에서 반가웠습니다. 다름이 아니라 부탁드릴것이 있어서 이렇게 연락드립니다.",
                time = "오전 10:24"
            ),
            ChatVo(
                message = "아 죄송해요, 이제야 메세지를 봤습니다. 저도 반가웠습니다! 어떤 부탁인가요?",
                time = "오전 10:27",
                isMy = true
            ),
            ChatVo(
                message = "안녕하세요.",
                time = "오전 10:23"
            ),
            ChatVo(
                message = "안녕하세요.",
                time = "오전 10:23",
                isMy = true
            ),
            ChatVo(
                message = "안녕하세요. 김이름님 잘지내셨나요? 일전에 UX세미나에서 반가웠습니다. 다름이 아니라 부탁드릴것이 있어서 이렇게 연락드립니다.",
                time = "오전 10:24"
            ),
            ChatVo(
                message = "아 죄송해요, 이제야 메세지를 봤습니다. 저도 반가웠습니다! 어떤 부탁인가요?",
                time = "오전 10:27",
                isMy = true
            )
        )
        _state.value = ChatState.OnDataLoaded
    }
}
