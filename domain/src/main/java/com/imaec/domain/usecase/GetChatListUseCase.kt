package com.imaec.domain.usecase

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.model.ChatListDto
import com.imaec.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetChatListUseCase @Inject constructor(
    private val repository: ChatRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, List<ChatListDto>>(dispatcher) {

    override suspend fun execute(parameters: String): List<ChatListDto> =
        repository.getChatList(parameters)
}
