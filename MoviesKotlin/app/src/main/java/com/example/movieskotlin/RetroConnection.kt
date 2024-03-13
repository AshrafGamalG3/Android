package com.example.movieskotlin

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetroConnection {

    val retrofit:Retrofit =Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).build()


   val getApiCalls = retrofit.create(ApiCalls::class.java)
}