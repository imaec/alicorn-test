package com.imaec.domain.repository

import android.accounts.Account
import com.imaec.domain.model.MemberInfoDto

interface MemberRepository {

    val account: Account?
    val memberInfo: MemberInfoDto?
    val isSignIn: Boolean

    suspend fun login(id: String, password: String): Boolean

    suspend fun logout()
}
