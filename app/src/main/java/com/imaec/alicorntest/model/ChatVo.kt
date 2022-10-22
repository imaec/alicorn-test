package com.imaec.alicorntest.model

data class ChatVo(
    val message: String,
    val time: String,
    val isMy: Boolean = false
)
