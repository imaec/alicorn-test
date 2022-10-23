package com.imaec.domain.usecase

import com.imaec.domain.IoDispatcher
import com.imaec.domain.NoParamUseCase
import com.imaec.domain.repository.MemberRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: MemberRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NoParamUseCase<Unit>(dispatcher) {

    override suspend fun execute() {
        repository.logout()
    }
}
