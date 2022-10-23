package com.imaec.alicorntest.ui.chat

sealed class ChatState {
    object OnDataLoaded : ChatState()
    data class ShowToast(val message: String) : ChatState()
}
