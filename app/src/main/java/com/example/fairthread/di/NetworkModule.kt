package com.example.fairthread.di

import com.example.fairthread.data.api.EmailReputationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    val emailApi: EmailReputationApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://emailreputation.abstractapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmailReputationApi::class.java)
    }
}