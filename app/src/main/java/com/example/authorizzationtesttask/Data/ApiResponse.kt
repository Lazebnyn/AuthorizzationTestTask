package com.example.authorizzationtesttask.Data


data class ApiResponse(
    val success: String,
    val error: ApiError,
    val payments: List<Payment>?
)

data class ApiError(
    val error_code: Int,
    val error_msg: String
)
