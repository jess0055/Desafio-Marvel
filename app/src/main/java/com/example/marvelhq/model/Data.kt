package com.example.marvelhq.model

import java.io.Serializable

data class Data(
    val count: String,
    val limit: String,
    val offset: Int,
    val results: List<Result>,
    val total: String
): Serializable