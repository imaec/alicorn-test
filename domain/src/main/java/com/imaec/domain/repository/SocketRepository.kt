package com.imaec.domain.repository

interface SocketRepository {

    fun connect(chatId: String, listener: StompSocketListener)
    fun disconnect()
    fun send(message: String)
    fun isConnect(): Boolean
}

interface StompSocketListener {
    fun onOpen()
    fun onMessage(message: String)
    fun onClose()
    fun onRetry(count: Int, errorMessage: String)
    fun onRetryFail(ex: Exception?)
    fun onError(ex: Exception?)
    fun onEtc(message: String)
    fun onThrowable(t: Throwable?)
}
