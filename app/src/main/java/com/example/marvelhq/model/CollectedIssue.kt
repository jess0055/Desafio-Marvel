package com.example.marvelhq.model

import java.io.Serializable

data class CollectedIssue(
    val name: String,
    val resourceURI: String
): Serializable