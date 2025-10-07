package com.example.fairthread.data.remote

import com.example.fairthread.model.EmailValidationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AbstractApiService {
    @GET("v1/")
    suspend fun validateEmail(
        @Query("api_key") apiKey: String,
        @Query("email") email: String
    ): EmailValidationResponse
}
