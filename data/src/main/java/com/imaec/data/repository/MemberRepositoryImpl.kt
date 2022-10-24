package com.imaec.data.repository

import android.accounts.Account
import android.accounts.AccountManager
import android.os.Build
import com.imaec.data.api.AlicornMemberService
import com.imaec.data.api.body.LoginBody
import com.imaec.data.entity.ConnectedPeopleEntity.Companion.toDto
import com.imaec.domain.model.ConnectedPeopleDto
import com.imaec.domain.model.MemberInfoDto
import com.imaec.domain.repository.MemberRepository
import java.lang.Exception

class MemberRepositoryImpl(
    private val service: AlicornMemberService,
    private val accountManager: AccountManager
) : MemberRepository {

    override val account: Account?
        get() = accountManager.getAccountsByType(ACCOUNT_TYPE).firstOrNull()

    override val memberInfo: MemberInfoDto?
        get() = if (account != null) {
            try {
                MemberInfoDto(
                    uid = accountManager.getUserData(account, MEMBER_UID),
                    id = accountManager.getUserData(account, MEMBER_ID)
                )
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }

    override val isSignIn: Boolean
        get() = memberInfo != null

    override suspend fun login(id: String, password: String): Boolean {
        val uid = service.login(LoginBody(id, password)).uid ?: return false
        return updateMemberInfo(uid, id)
    }

    private fun updateMemberInfo(uid: String, id: String): Boolean {
        if (account != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                accountManager.removeAccountExplicitly(account)
            } else {
                accountManager.removeAccount(account, null, null)
            }
        }
        val created = accountManager.addAccountExplicitly(
            Account(uid, ACCOUNT_TYPE),
            null,
            null
        )
        with(accountManager) {
            setUserData(account, MEMBER_UID, uid)
            setUserData(account, MEMBER_ID, id)
        }

        return created
    }

    override suspend fun logout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            accountManager.removeAccountExplicitly(account)
        } else {
            accountManager.removeAccount(account, null, null)
        }
    }

    override suspend fun searchConnectedPeopleList(
        uid: String,
        keyword: String
    ): List<ConnectedPeopleDto> =
        service.searchConnectedPeopleList(uid, keyword).connectedPeopleList.map(::toDto)

    companion object {
        private const val ACCOUNT_TYPE = "com.imaec.alicorntest.auth"
        private const val MEMBER_UID = "MEMBER_UID"
        private const val MEMBER_ID = "MEMBER_ID"
    }
}
