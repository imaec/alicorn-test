package com.imaec.domain.repository

interface SocketRepository {

    fun <T> connect(destination: String, listener: StompSocketListener<T>)
    fun disconnect()
    fun send(message: String)
    fun isConnect(): Boolean
}

interface StompSocketListener<in T> {
    fun onMessage(message: T)
    fun onError(ex: Exception?)
    fun onEtc(message: String)
    fun onThrowable(t: Throwable?)
}
