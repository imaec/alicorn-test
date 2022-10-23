package com.imaec.domain.usecase

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.repository.MemberRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: MemberRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<LoginParams, Boolean>(dispatcher) {

    override suspend fun execute(parameters: LoginParams): Boolean =
        repository.login(parameters.id, parameters.password)
}

data class LoginParams(val id: String, val password: String)
