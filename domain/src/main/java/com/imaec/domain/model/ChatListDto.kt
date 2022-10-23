package com.imaec.domain.model

data class ChatListDto(
    val chatId: String,
    val profile: String,
    val name: String,
    val time: String,
    val message: String,
    val unread: Int
)
