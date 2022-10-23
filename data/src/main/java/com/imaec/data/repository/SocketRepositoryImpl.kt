package com.imaec.data.repository

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

    override fun connect(chatId: String, listener: StompSocketListener) {
        with(stompClient) {
            connect()
            lifecycle().subscribe(
                { lifeCycleEvent ->
                    when (lifeCycleEvent.type) {
                        LifecycleEvent.Type.OPENED -> {
                            listener.onOpen()
                        }
                        LifecycleEvent.Type.CLOSED -> {
                            disposable.clear()
                            listener.onClose()
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
            topic("/chat/$chatId").subscribe {
                listener.onMessage(it.payload)
            }.add()
        }
    }

    override fun disconnect() {
        stompClient.disconnect()
    }

    override fun send(message: String) {
        stompClient.send(message)
    }

    override fun isConnect(): Boolean = stompClient.isConnected

    private fun Disposable.add() {
        disposable.add(this)
    }
}
