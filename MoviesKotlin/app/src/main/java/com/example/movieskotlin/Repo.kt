package com.example.movieskotlin

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repo{

    fun getMovie(movieCallback: (MovieModel?)->Unit ){
   RetroConnection.getApiCalls.getMovies().enqueue(object : Callback<MovieModel?> {
       override fun onResponse(call: Call<MovieModel?>, response: Response<MovieModel?>) {
           movieCallback.invoke(response.body())
       }

       override fun onFailure(call: Call<MovieModel?>, t: Throwable) {
           TODO("Not yet implemented")
       }
   })
    }
}