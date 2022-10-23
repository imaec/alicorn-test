package com.imaec.domain.usecase

import com.imaec.domain.repository.SocketRepository
import com.imaec.domain.repository.StompSocketListener
import javax.inject.Inject

class SocketUseCase @Inject constructor(
    private val socketRepository: SocketRepository
) {

    fun <T> connect(destination: String, listener: StompSocketListener<T>) {
        socketRepository.connect(destination, listener)
    }

    fun disconnect() {
        socketRepository.disconnect()
    }

    fun send(message: String) {
        socketRepository.send(message)
    }

    fun isConnect() = socketRepository.isConnect()
}
