package com.imaec.domain.usecase

import com.imaec.domain.model.MemberInfoDto
import com.imaec.domain.repository.MemberRepository
import javax.inject.Inject

class GetMemberInfoUseCase @Inject constructor(
    private val repository: MemberRepository
) {
    operator fun invoke(): MemberInfoDto? = repository.memberInfo
}
