package com.example.fairthread.data.api

import com.example.fairthread.data.api.dto.CategoryDto
import com.example.fairthread.data.api.dto.ProductDto
import com.example.fairthread.data.api.dto.StoreDto
import com.example.fairthread.model.Category
import com.example.fairthread.model.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FairThreadApi {

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("products")
    suspend fun getProducts(): List<Product>


    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId: String): ProductDto

    @GET("stores")
    suspend fun getStores(): List<StoreDto>

    @GET("stores/{id}")
    suspend fun getStoreById(@Path("id") storeId: String): StoreDto
}