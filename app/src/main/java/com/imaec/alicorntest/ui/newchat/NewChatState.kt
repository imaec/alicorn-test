package com.imaec.alicorntest.ui.newchat

import com.imaec.alicorntest.model.ConnectedPeopleVo

sealed class NewChatState {

    data class OnClickPeople(val chatId: String, val item: ConnectedPeopleVo) : NewChatState()
}
