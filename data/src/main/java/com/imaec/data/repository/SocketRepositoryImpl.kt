package com.imaec.data.repository

import com.google.gson.Gson
import com.imaec.domain.model.ChatDto
import com.imaec.domain.repository.SocketRepository
import com.imaec.domain.repository.StompSocketListener
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent

class SocketRepositoryImpl(
    private val stompClient: StompClient
) : SocketRepository {

    private val disposable = CompositeDisposable()

    @Suppress("UNCHECKED_CAST")
    override fun <T> connect(destination: String, listener: StompSocketListener<T>) {
        with(stompClient) {
            connect()
            lifecycle().subscribe(
                { lifeCycleEvent ->
                    when (lifeCycleEvent.type) {
                        LifecycleEvent.Type.CLOSED -> {
                            disposable.clear()
                        }
                        LifecycleEvent.Type.ERROR -> {
                            listener.onError(lifeCycleEvent.exception)
                        }
                        else -> listener.onEtc(lifeCycleEvent.message)
                    }
                },
                {
                    listener.onThrowable(it)
                }
            ).add()
            topic(destination).subscribe(
                {
                    listener.onMessage(Gson().fromJson(it.payload, ChatDto::class.java) as T)
                },
                {
                    listener.onThrowable(it)
                }
            ).add()
        }
    }

    override fun disconnect() {
        stompClient.disconnect()
    }

    override fun send(message: String) {
        stompClient.send(message).subscribe(
            {
            },
            {
            }
        ).add()
    }

    override fun isConnect(): Boolean = stompClient.isConnected

    private fun Disposable.add() {
        disposable.add(this)
    }
}
