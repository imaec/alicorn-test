package com.imaec.alicorntest.model

import com.imaec.domain.model.ChatDto

data class ChatVo(
    val message: String,
    val time: String,
    val isMy: Boolean
) {
    companion object {
        fun dtoToVo(dto: ChatDto) = ChatVo(
            message = dto.message,
            time = dto.time,
            isMy = dto.isMy
        )
    }
}
