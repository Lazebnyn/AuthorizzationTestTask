package com.example.authorizzationtesttask.repository

import com.example.authorizzationtesttask.Data.ApiResponse
import com.example.authorizzationtesttask.Data.LoginRequest
import com.example.authorizzationtesttask.Data.Payment
import com.example.authorizzationtesttask.network.ApiException

import com.example.authorizzationtesttask.network.MyApi


class MyRepository(private val myApi: MyApi) {

    suspend fun login(username: String, password: String): ApiResponse {
        val response = myApi.login(LoginRequest(username, password))
        if (response.success == "false") {
            throw ApiException(response.error.error_msg)
        }
        return response
    }

    suspend fun logout(token: String): ApiResponse {
        val response = myApi.logout(token)
        if (response.success == "false") {
            throw ApiException(response.error.error_msg)
        }
        return response
    }

    suspend fun getPayments(token: String): ApiResponse {
        val response = myApi.getPayments(token)
        if (response.success == "false") {
            throw ApiException(response.error.error_msg)
        }
        return response
    }
}
