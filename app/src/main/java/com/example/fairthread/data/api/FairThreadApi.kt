package com.example.fairthread.data.api

import com.example.fairthread.data.api.dto.CategoryDto
import com.example.fairthread.data.api.dto.ProductDto
import com.example.fairthread.data.api.dto.StoreDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FairThreadApi {

    @GET("catalogue")
    suspend fun getCategories(): List<CategoryDto>

    @GET("products")
    suspend fun getProductsByCategory(@Query("category") category: String): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId: String): ProductDto

    @GET("stores")
    suspend fun getStores(): List<StoreDto>

    @GET("stores/{id}")
    suspend fun getStoreById(@Path("id") storeId: String): StoreDto
}