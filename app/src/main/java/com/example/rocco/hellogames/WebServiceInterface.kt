package com.example.rocco.hellogames

import retrofit2.Call
import retrofit2.http.GET

interface WebServiceInterface {
    @GET("game/list")
    fun ListGames(): Call<List<Games>>
}