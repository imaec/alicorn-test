package com.imaec.data.di

import com.imaec.data.api.AlicornService
import com.imaec.data.repository.ChatRepositoryImpl
import com.imaec.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideChatRepository(
        service: AlicornService
    ): ChatRepository = ChatRepositoryImpl(service)
}
