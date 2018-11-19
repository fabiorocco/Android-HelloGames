package com.example.rocco.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceInterface {
    @GET("game/list")
    fun ListGames(): Call<List<Games>>

    @GET("game/details")
    fun DescGame(@Query("game_id") id: Int): Call<Games>
}