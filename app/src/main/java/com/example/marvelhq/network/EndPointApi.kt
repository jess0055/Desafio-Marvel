package com.example.marvelhq.network

import com.example.marvelhq.model.ComicResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EndPointApi {

    @GET("comics?")
    suspend fun getResponseComics(
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?,
        @Query("format") format: String?,
        @Query("orderBy") orderBy: String?,
        @Query("ts") ts: String?,
        @Query("hash") hash: String?,
        @Query("apikey") apiKey: String?) : ComicResponse

    @GET("comics/{comicId}")
    suspend fun getResponseComicsById(
        @Path("comicId") comicId: Int?,
        @Query("orderBy") orderBy: String?,
        @Query("ts") ts: String?,
        @Query("hash") hash: String?,
        @Query("apikey") apiKey: String?) : ComicResponse



}