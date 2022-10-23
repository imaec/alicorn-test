package com.imaec.data.api

import com.imaec.data.api.body.LoginBody
import com.imaec.data.entity.LoginEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface AlicornMemberService {

    @POST("fake/member/login")
    suspend fun login(
        @Body body: LoginBody
    ): LoginEntity
}
