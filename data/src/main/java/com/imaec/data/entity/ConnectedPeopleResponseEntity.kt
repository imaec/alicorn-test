package com.imaec.data.entity

import com.imaec.domain.model.ConnectedPeopleDto

data class ConnectedPeopleResponseEntity(
    val connectedPeopleList: List<ConnectedPeopleEntity>
)

data class ConnectedPeopleEntity(
    val uid: String,
    val profile: String,
    val name: String,
) {
    companion object {
        fun toDto(entity: ConnectedPeopleEntity) = ConnectedPeopleDto(
            uid = entity.uid,
            profile = entity.profile,
            name = entity.name
        )
    }
}
