package com.imaec.data.api

import com.imaec.data.api.body.LoginBody
import com.imaec.data.entity.ConnectedPeopleResponseEntity
import com.imaec.data.entity.LoginEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AlicornMemberService {

    @POST("fake/member/login")
    suspend fun login(
        @Body body: LoginBody
    ): LoginEntity

    @GET("fake/member/connected/search")
    suspend fun searchConnectedPeopleList(
        @Query("uid") uid: String,
        @Query("keyword") keyword: String
    ): ConnectedPeopleResponseEntity
}
