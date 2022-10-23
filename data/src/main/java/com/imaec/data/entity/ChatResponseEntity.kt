package com.imaec.data.entity

import com.imaec.domain.model.ChatDto

data class ChatResponseEntity(
    val chat: List<ChatEntity>
)

data class ChatEntity(
    val message: String,
    val time: String,
    val isMy: Boolean
) {
    companion object {
        fun toDto(entity: ChatEntity) = ChatDto(
            message = entity.message,
            time = entity.time,
            isMy = entity.isMy
        )
    }
}
