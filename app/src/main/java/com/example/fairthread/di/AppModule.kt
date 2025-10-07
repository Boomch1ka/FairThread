package com.example.fairthread.di

import com.example.fairthread.data.remote.AbstractApiService
import com.example.fairthread.data.repository.AuthRepository
import com.example.fairthread.viewmodel.AuthViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    private const val BASE_URL = "https://emailvalidation.abstractapi.com/"
    private const val API_KEY = "https://emailvalidation.abstractapi.com/v1/?api_key=6177faa45a4c40a9974896ecad4aca53&email=darsh.pyda@gmail.com"

    /*//  This is your private API key, specific to this API.
Primary Key:6177faa45a4c40a9974896ecad4aca53 */

    // Retrofit instance
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Abstract API service
    private val abstractApiService: AbstractApiService =
        retrofit.create(AbstractApiService::class.java)

    // AuthRepository with injected Abstract API
    val authRepository: AuthRepository = AuthRepository(abstractApi = abstractApiService)

    // AuthViewModel
    val authViewModel: AuthViewModel = AuthViewModel(authRepository)
}
