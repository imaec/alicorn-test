package com.imaec.alicorntest.model

import com.imaec.domain.model.ConnectedPeopleDto

data class ConnectedPeopleVo(
    val uid: String,
    val profile: String,
    val name: String
) {
    companion object {
        fun dtoToVo(dto: ConnectedPeopleDto) = ConnectedPeopleVo(
            uid = dto.uid,
            profile = dto.profile,
            name = dto.name
        )
    }
}
