package com.imaec.data.api

import com.google.gson.Gson
import com.imaec.data.api.body.LoginBody
import com.imaec.data.entity.ChatEntity
import com.imaec.data.entity.ChatListEntity
import com.imaec.data.entity.ChatListResponseEntity
import com.imaec.data.entity.ChatResponseEntity
import com.imaec.data.entity.LoginEntity
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer

object MockDataManager {

    fun getFakeResponse(chain: Interceptor.Chain): Response {
        return when (chain.request().url.encodedPath) {
            "/fake/chat/list" -> getResponse(chain, Gson().toJson(ChatListResponseEntity(chatList)))
            "/fake/chat" -> getResponse(chain, Gson().toJson(ChatResponseEntity(chat)))
            "/fake/member/login" -> fakeLogin(chain)
            else -> chain.proceed(chain.request())
        }
    }

    private fun getResponse(chain: Interceptor.Chain, response: String?) = Response.Builder()
        .protocol(Protocol.HTTP_1_1)
        .addHeader("content-type", "application/json")
        .request(chain.request())
        .message(response ?: "")
        .code(200)
        .body(response?.toResponseBody())
        .build()

    private fun fakeLogin(chain: Interceptor.Chain): Response {
        val buffer = Buffer()
        chain.request().body?.writeTo(buffer)
        val body = Gson().fromJson(buffer.readUtf8(), LoginBody::class.java)
        val id = body.id
        val password = body.password

        return getResponse(
            chain,
            Gson().toJson(
                LoginEntity(
                    members.firstOrNull {
                        id == it.second && password == it.third
                    }?.first
                )
            )
        )
    }
}

val members = listOf(
    Triple("0", "kim", "1234"),
    Triple("1", "lee", "1234"),
    Triple("2", "kang", "1212"),
    Triple("3", "park", "4321"),
    Triple("4", "kim2", "1111")
)

val chatList = listOf(
    ChatListEntity(
        chatId = "0",
        profile = "",
        name = "김이름",
        time = "오후 12:38",
        message = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
        unread = 0
    ),
    ChatListEntity(
        chatId = "1",
        profile = "",
        name = "김이름",
        time = "오후 12:38",
        message = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
        unread = 0
    ),
    ChatListEntity(
        chatId = "2",
        profile = "",
        name = "김이름",
        time = "오후 12:38",
        message = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
        unread = 0
    ),
    ChatListEntity(
        chatId = "3",
        profile = "",
        name = "김이름",
        time = "오후 12:38",
        message = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
        unread = 0
    ),
    ChatListEntity(
        chatId = "4",
        profile = "",
        name = "김이름",
        time = "오후 12:38",
        message = "안녕하세요. 김이름님 잘 지내셨나요. 오랜만입니다.",
        unread = 0
    )
)

val chat = listOf(
    ChatEntity(
        message = "안녕하세요.",
        time = "오전 10:23",
        isMy = false
    ),
    ChatEntity(
        message = "안녕하세요.",
        time = "오전 10:23",
        isMy = true
    ),
    ChatEntity(
        message = "안녕하세요. 김이름님 잘지내셨나요? 일전에 UX세미나에서 반가웠습니다. 다름이 아니라 부탁드릴것이 있어서 이렇게 연락드립니다.",
        time = "오전 10:24",
        isMy = false
    ),
    ChatEntity(
        message = "아 죄송해요, 이제야 메세지를 봤습니다. 저도 반가웠습니다! 어떤 부탁인가요?",
        time = "오전 10:27",
        isMy = true
    ),
    ChatEntity(
        message = "안녕하세요.",
        time = "오전 10:28",
        isMy = false
    ),
    ChatEntity(
        message = "안녕하세요.",
        time = "오전 10:28",
        isMy = true
    ),
    ChatEntity(
        message = "안녕하세요. 김이름님 잘지내셨나요? 일전에 UX세미나에서 반가웠습니다. 다름이 아니라 부탁드릴것이 있어서 이렇게 연락드립니다.",
        time = "오전 10:29",
        isMy = false
    ),
    ChatEntity(
        message = "아 죄송해요, 이제야 메세지를 봤습니다. 저도 반가웠습니다! 어떤 부탁인가요?",
        time = "오전 10:31",
        isMy = true
    ),
    ChatEntity(
        message = "안녕하세요.",
        time = "오전 10:32",
        isMy = false
    ),
    ChatEntity(
        message = "안녕하세요.",
        time = "오전 10:33",
        isMy = true
    ),
    ChatEntity(
        message = "안녕하세요. 김이름님 잘지내셨나요? 일전에 UX세미나에서 반가웠습니다. 다름이 아니라 부탁드릴것이 있어서 이렇게 연락드립니다.",
        time = "오전 10:33",
        isMy = false
    ),
    ChatEntity(
        message = "아 죄송해요, 이제야 메세지를 봤습니다. 저도 반가웠습니다! 어떤 부탁인가요? https://www.naver.com/",
        time = "오전 11:27",
        isMy = true
    )
)
