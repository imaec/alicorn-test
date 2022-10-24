package com.imaec.data.api

import com.imaec.data.api.body.ChatBody
import com.imaec.data.api.body.ChatListBody
import com.imaec.data.entity.ChatIdEntity
import com.imaec.data.entity.ChatListResponseEntity
import com.imaec.data.entity.ChatResponseEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AlicornService {

    @POST("fake/chat/list")
    suspend fun getChatList(
        @Body body: ChatListBody
    ): ChatListResponseEntity

    @POST("fake/chat")
    suspend fun getChat(
        @Body body: ChatBody
    ): ChatResponseEntity

    @GET("fake/chat/id")
    suspend fun getChatId(
        @Query("uid1") uid1: String,
        @Query("uid2") uid2: String
    ): ChatIdEntity
}
