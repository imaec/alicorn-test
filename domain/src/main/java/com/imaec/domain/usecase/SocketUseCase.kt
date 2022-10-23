package com.imaec.domain.usecase

import com.imaec.domain.repository.SocketRepository
import com.imaec.domain.repository.StompSocketListener
import javax.inject.Inject

class SocketUseCase @Inject constructor(
    private val socketRepository: SocketRepository
) {

    fun connect(chatId: String, listener: StompSocketListener) {
        socketRepository.connect(chatId, listener)
    }

    fun disconnect() {
        socketRepository.disconnect()
    }

    fun send(message: String) {
        socketRepository.send(message)
    }

    fun isConnect() = socketRepository.isConnect()
}
