package com.imaec.data.api

import com.imaec.data.api.body.ChatBody
import com.imaec.data.api.body.ChatListBody
import com.imaec.data.entity.ChatListResponseEntity
import com.imaec.data.entity.ChatResponseEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface AlicornService {

    @POST("fake/chat/list")
    suspend fun getChatList(
        @Body body: ChatListBody
    ): ChatListResponseEntity

    @POST("fake/chat")
    suspend fun getChat(
        @Body body: ChatBody
    ): ChatResponseEntity
}
