package com.imaec.data.entity

import com.imaec.domain.model.ChatListDto

data class ChatListResponseEntity(
    val chatList: List<ChatListEntity>
)

data class ChatListEntity(
    val chatId: String,
    val profile: String,
    val name: String,
    val time: String,
    val message: String,
    val unread: Int
) {
    companion object {
        fun toDto(entity: ChatListEntity) = ChatListDto(
            chatId = entity.chatId,
            profile = entity.profile,
            name = entity.name,
            time = entity.time,
            message = entity.message,
            unread = entity.unread
        )
    }
}
