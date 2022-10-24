package com.imaec.domain.usecase

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.model.ConnectedPeopleDto
import com.imaec.domain.repository.MemberRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchConnectedPeopleUseCase @Inject constructor(
    private val repository: MemberRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<SearchConnectedPeopleParams, List<ConnectedPeopleDto>>(dispatcher) {

    override suspend fun execute(parameters: SearchConnectedPeopleParams): List<ConnectedPeopleDto> =
        repository.searchConnectedPeopleList(parameters.uid, parameters.keyword)
}

data class SearchConnectedPeopleParams(val uid: String, val keyword: String)
