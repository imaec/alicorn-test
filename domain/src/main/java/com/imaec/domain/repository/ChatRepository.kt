package com.imaec.domain.repository

import com.imaec.domain.model.ChatListDto

interface ChatRepository {

    suspend fun getChatList(id: String): List<ChatListDto>
}
