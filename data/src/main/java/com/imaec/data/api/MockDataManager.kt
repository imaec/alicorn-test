package com.imaec.data.api

import com.google.gson.Gson
import com.imaec.data.entity.ChatListEntity
import com.imaec.data.entity.ChatListResponseEntity
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

object MockDataManager {

    fun getFakeResponse(chain: Interceptor.Chain): Response {
        return if (chain.request().url.pathSegments.contains("fake")) {
            when (chain.request().url.encodedPath) {
                "/fake/chat/list" -> getChatListResponse(chain)
                "/fake/chat" -> getChatResponse(chain)
                else -> chain.proceed(chain.request())
            }
        } else {
            chain.proceed(chain.request())
        }
    }

    private fun getChatListResponse(chain: Interceptor.Chain): Response {
        val response = Gson().toJson(ChatListResponseEntity(chatList))
        return Response.Builder()
            .protocol(Protocol.HTTP_1_1)
            .addHeader("content-type", "application/json")
            .request(chain.request())
            .message(response)
            .code(200)
            .body(response.toResponseBody())
            .build()
    }

    private fun getChatResponse(chain: Interceptor.Chain): Response {
        val response = Gson().toJson(ChatListResponseEntity(chatList))
        return Response.Builder()
            .protocol(Protocol.HTTP_1_1)
            .addHeader("content-type", "application/json")
            .request(chain.request())
            .message(response)
            .code(200)
            .body(response.toResponseBody())
            .build()
    }
}

val chatList = listOf(
    ChatListEntity(
        profile = "",
        name = "김이름",
        time = "오후 12:38",
        message = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
        unread = 0
    ),
    ChatListEntity(
        profile = "",
        name = "김이름",
        time = "오후 12:38",
        message = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
        unread = 0
    ),
    ChatListEntity(
        profile = "",
        name = "김이름",
        time = "오후 12:38",
        message = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
        unread = 0
    ),
    ChatListEntity(
        profile = "",
        name = "김이름",
        time = "오후 12:38",
        message = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
        unread = 0
    ),
    ChatListEntity(
        profile = "",
        name = "김이름",
        time = "오후 12:38",
        message = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
        unread = 0
    )
)
