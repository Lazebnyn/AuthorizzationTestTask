package com.example.authorizzationtesttask.network

import com.example.authorizzationtesttask.Data.ApiResponse
import com.example.authorizzationtesttask.Data.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MyApi {

    @GET("payments")
    suspend fun getPayments(@Header("Authorization") token: String): ApiResponse

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): ApiResponse

    @POST("logout")
    suspend fun logout(@Header("Authorization") token: String): ApiResponse
}

