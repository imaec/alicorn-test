package com.imaec.domain.usecase

import com.imaec.domain.MainDispatcher
import com.imaec.domain.NoParamUseCase
import com.imaec.domain.repository.MemberRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class IsLoginUseCase @Inject constructor(
    private val repository: MemberRepository,
    @MainDispatcher coroutineDispatcher: CoroutineDispatcher
) : NoParamUseCase<Boolean>(coroutineDispatcher) {

    override suspend fun execute(): Boolean = repository.isSignIn
}
