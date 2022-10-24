package com.imaec.domain.usecase

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetChatIdUseCase @Inject constructor(
    private val repository: ChatRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetChatIdParams, String>(dispatcher) {

    override suspend fun execute(parameters: GetChatIdParams): String =
        repository.getChatId(parameters.uid1, parameters.uid2)
}

data class GetChatIdParams(val uid1: String, val uid2: String)
