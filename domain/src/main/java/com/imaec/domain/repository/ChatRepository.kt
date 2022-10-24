package com.imaec.domain.repository

import com.imaec.domain.model.ChatDto
import com.imaec.domain.model.ChatListDto

interface ChatRepository {

    suspend fun getChatList(id: String): List<ChatListDto>
    suspend fun getChat(chatId: String): List<ChatDto>
    suspend fun getChatId(uid1: String, uid2: String): String
}
