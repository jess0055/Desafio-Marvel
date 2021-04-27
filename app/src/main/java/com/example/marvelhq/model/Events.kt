package com.example.marvelhq.model

import java.io.Serializable

data class Events(
    val available: String,
    val collectionURI: String,
    val items: List<ItemXX>,
    val returned: String
): Serializable