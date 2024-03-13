package com.example.movieskotlin

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiCalls {

    @GET("discover/movie")
    fun getMovies(@retrofit2.http.Query("api_key") apiKey: String="03b409460cf77e5e523433ddfa291540"):Call<MovieModel>
}