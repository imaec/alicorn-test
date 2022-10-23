package com.imaec.alicorntest.ui.main

import com.imaec.alicorntest.model.ChatListVo

sealed class MainState {

    data class OnClickChat(val item: ChatListVo) : MainState()
    object StartLoginActivity : MainState()
    data class ShowToast(val message: String) : MainState()
}
