package com.example.flo_download

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SongRetrofitInterfaces {
    @GET("/songs")
    fun getSongs(): Call<SongResponse>
}