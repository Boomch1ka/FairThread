package com.example.fairthread.di

import com.example.fairthread.data.api.EmailValidationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    val emailApi: EmailValidationApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://emailvalidation.abstractapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmailValidationApi::class.java)
    }
}