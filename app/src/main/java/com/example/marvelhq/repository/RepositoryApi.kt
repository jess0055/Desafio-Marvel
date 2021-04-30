package com.example.marvelhq.repository

import com.example.marvelhq.util.MD5.Companion.md5
import com.example.marvelhq.model.ComicResponse
import com.example.marvelhq.network.EndPointApi
import com.example.marvelhq.network.RetrofitInit

class RepositoryApi {

    private var url = "https://gateway.marvel.com/v1/public/"

    private val PUBLIC_KEY : String? = "6eb7e8896ec5850c52515a8a23ee97f0"
    private val PRIVATE_KEY: String? ="0dd0c16fedb8a02985977eafca66b49f5e6a526f"
    private val orderBy: String? = "-onsaleDate"
    private val format: String? = "comic"
    private val limit: Int? = 50

    var ts: String? = java.lang.Long.toString(System.currentTimeMillis() / 1000)
    var hash: String? = md5(ts + PRIVATE_KEY + PUBLIC_KEY)

    private var service = EndPointApi::class
    private val serviceMarvel = RetrofitInit(url).create(service)

    suspend fun getComicsService(page: Int = 0): ComicResponse {
        return serviceMarvel.getResponseComics(page, limit, format, orderBy, ts, hash, PUBLIC_KEY)
    }

    suspend fun getComicByIdService(comicId: Int): ComicResponse {
        return serviceMarvel.getResponseComicsById(comicId, orderBy, ts, hash, PUBLIC_KEY)
    }
}