package com.example.marvelhq.model

import java.io.Serializable

data class TextObject(
    val language: String,
    val text: String,
    val type: String
): Serializable