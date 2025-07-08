package com.rerebo.library.utils

import com.rerebo.library.data.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api : ApiInterface by lazy{
        Retrofit.Builder()
            .baseUrl("https://api.artic.edu")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}