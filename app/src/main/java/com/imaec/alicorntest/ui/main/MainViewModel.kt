package com.imaec.alicorntest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaec.alicorntest.model.ChatListVo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _chatList = MutableLiveData<List<ChatListVo>>()
    val chatList: LiveData<List<ChatListVo>> get() = _chatList

    fun fetchData() {
        _chatList.value = listOf(
            ChatListVo(
                profile = "",
                name = "김이름",
                time = "오후 12:38",
                content = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
                unread = "0"
            ),
            ChatListVo(
                profile = "",
                name = "김이름",
                time = "오후 12:38",
                content = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
                unread = "0"
            ),
            ChatListVo(
                profile = "",
                name = "김이름",
                time = "오후 12:38",
                content = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
                unread = "0"
            ),
            ChatListVo(
                profile = "",
                name = "김이름",
                time = "오후 12:38",
                content = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
                unread = "0"
            ),
            ChatListVo(
                profile = "",
                name = "김이름",
                time = "오후 12:38",
                content = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
                unread = "0"
            )
        )
    }
}
