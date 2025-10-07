package com.example.fairthread.di

import com.example.fairthread.data.api.FairThreadApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://api.fairthread.com/"

    val api: FairThreadApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Replace with your actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FairThreadApi::class.java)
    }
}
