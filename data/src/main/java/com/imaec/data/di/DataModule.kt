package com.imaec.data.di

import android.accounts.AccountManager
import com.imaec.data.api.AlicornMemberService
import com.imaec.data.api.AlicornService
import com.imaec.data.repository.ChatRepositoryImpl
import com.imaec.data.repository.MemberRepositoryImpl
import com.imaec.data.repository.SocketRepositoryImpl
import com.imaec.domain.repository.ChatRepository
import com.imaec.domain.repository.MemberRepository
import com.imaec.domain.repository.SocketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.naiksoftware.stomp.StompClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideChatRepository(
        service: AlicornService
    ): ChatRepository = ChatRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideMemberRepository(
        service: AlicornMemberService,
        accountManager: AccountManager
    ): MemberRepository = MemberRepositoryImpl(service, accountManager)

    @Provides
    @Singleton
    fun provideSocketRepository(
        stompClient: StompClient
    ): SocketRepository = SocketRepositoryImpl(stompClient)
}
