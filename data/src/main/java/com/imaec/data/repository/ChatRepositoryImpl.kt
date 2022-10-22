package com.imaec.data.repository

import com.imaec.data.api.AlicornService
import com.imaec.data.api.body.ChatListBody
import com.imaec.data.entity.ChatListEntity.Companion.toDto
import com.imaec.domain.model.ChatListDto
import com.imaec.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val service: AlicornService
) : ChatRepository {

    override suspend fun getChatList(id: String): List<ChatListDto> =
        service.getChatList(ChatListBody(id)).chatList.map(::toDto)
}
