package com.example.marvelhq.network

import com.example.marvelhq.model.ComicResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPointApi {

    @GET("comics?")
    suspend fun getResponseComics(
        @Query("offset") offset: Int?,
        @Query("orderBy") orderBy: String?,
        @Query("ts") ts: String?,
        @Query("hash") hash: String?,
        @Query("apikey") apiKey: String?) : ComicResponse
}