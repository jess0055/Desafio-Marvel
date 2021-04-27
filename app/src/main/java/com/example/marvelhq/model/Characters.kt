package com.example.marvelhq.model

import java.io.Serializable

data class Characters(
    val available: String,
    val collectionURI: String,
    val items: List<Item>,
    val returned: String
) : Serializable