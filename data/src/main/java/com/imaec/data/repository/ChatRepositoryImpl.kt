package com.imaec.data.repository

import com.imaec.data.api.AlicornService
import com.imaec.data.api.body.ChatBody
import com.imaec.data.api.body.ChatListBody
import com.imaec.data.entity.ChatEntity
import com.imaec.data.entity.ChatListEntity
import com.imaec.domain.model.ChatDto
import com.imaec.domain.model.ChatListDto
import com.imaec.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val service: AlicornService
) : ChatRepository {

    override suspend fun getChatList(id: String): List<ChatListDto> =
        service.getChatList(ChatListBody(id)).chatList.map(ChatListEntity::toDto)

    override suspend fun getChat(chatId: String): List<ChatDto> =
        service.getChat(ChatBody(chatId)).chat.map(ChatEntity::toDto)
}
