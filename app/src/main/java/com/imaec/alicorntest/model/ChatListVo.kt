package com.imaec.alicorntest.model

import com.imaec.domain.model.ChatListDto

data class ChatListVo(
    val chatId: String,
    val profile: String,
    val name: String,
    val time: String,
    val message: String,
    val unread: String
) {
    companion object {
        fun dtoToVo(dto: ChatListDto) = ChatListVo(
            chatId = dto.chatId,
            profile = dto.profile,
            name = dto.name,
            time = dto.time,
            message = dto.message,
            unread = dto.unread.toString()
        )
    }
}
