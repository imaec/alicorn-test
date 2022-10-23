package com.imaec.domain.usecase

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.model.ChatDto
import com.imaec.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetChatUseCase @Inject constructor(
    private val repository: ChatRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, List<ChatDto>>(dispatcher) {

    override suspend fun execute(parameters: String): List<ChatDto> =
        repository.getChat(parameters)
}
