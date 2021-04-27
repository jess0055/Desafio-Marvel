package com.example.marvelhq.model

import java.io.Serializable

data class Stories(
    val available: String,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: String
):Serializable