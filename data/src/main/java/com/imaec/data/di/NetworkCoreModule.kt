package com.imaec.data.di

import android.accounts.AccountManager
import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.imaec.data.BuildConfig
import com.imaec.data.api.AlicornMemberService
import com.imaec.data.api.AlicornService
import com.imaec.data.api.MockDataManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkCoreModule {

    @Provides
    @Singleton
    fun provideAccountManager(@ApplicationContext context: Context): AccountManager =
        AccountManager.get(context)

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    if (!message.startsWith("{") && !message.startsWith("[")) {
                        Timber.tag("OkHttp").d(message)
                        return
                    }

                    try {
                        Timber.tag("OkHttp").d(
                            GsonBuilder().setPrettyPrinting().create()
                                .toJson(JsonParser().parse(message))
                        )
                    } catch (m: JsonSyntaxException) {
                        Timber.tag("OkHttp").d(message)
                    }
                }
            }
        ).apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideMockInterceptor(): Interceptor = Interceptor {
        MockDataManager.getFakeResponse(it)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        mockInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(mockInterceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        converterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder = Retrofit
        .Builder()
        .baseUrl("https://api.com")
        .client(okHttpClient)
        .addConverterFactory(converterFactory)

    @Provides
    @Singleton
    fun provideAlicornService(
        retrofitBuilder: Retrofit.Builder
    ): AlicornService = retrofitBuilder.build().create(AlicornService::class.java)

    @Provides
    @Singleton
    fun provideAlicornMemberService(
        retrofitBuilder: Retrofit.Builder
    ): AlicornMemberService = retrofitBuilder.build().create(AlicornMemberService::class.java)

    @Provides
    @Singleton
    fun provideStompClient(): StompClient =
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://api.com/chat/websocket").apply {
            withClientHeartbeat(1000)
        }
}
