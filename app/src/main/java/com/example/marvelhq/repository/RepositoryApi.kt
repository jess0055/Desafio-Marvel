package com.example.marvelhq.repository

import com.example.marvelhq.MD5.Companion.md5
import com.example.marvelhq.model.ComicResponse
import com.example.marvelhq.network.EndPointApi
import com.example.marvelhq.network.RetrofitInit

class RepositoryApi {

    private var url = "https://gateway.marvel.com/v1/public/"

    val PUBLIC_KEY : String? = "6eb7e8896ec5850c52515a8a23ee97f0"
    val PRIVATE_KEY: String? ="0dd0c16fedb8a02985977eafca66b49f5e6a526f"
    val orderBy: String? = "-title"

    var ts: String? = java.lang.Long.toString(System.currentTimeMillis() / 1000)
    var hash: String? = md5(ts + PRIVATE_KEY + PUBLIC_KEY)

    private var service = EndPointApi::class
    private val serviceMarvel = RetrofitInit(url).create(service)

    suspend fun getComicsService(page: Int = 0): ComicResponse {
        return serviceMarvel.getResponseComics(page, orderBy, ts, hash, PUBLIC_KEY)
    }
}