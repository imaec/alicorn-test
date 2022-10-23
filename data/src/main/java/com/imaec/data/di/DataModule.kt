package com.imaec.data.di

import com.imaec.data.api.AlicornService
import com.imaec.data.repository.ChatRepositoryImpl
import com.imaec.data.repository.SocketRepositoryImpl
import com.imaec.domain.repository.ChatRepository
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
    fun provideSocketRepository(
        stompClient: StompClient
    ): SocketRepository = SocketRepositoryImpl(stompClient)
}
