package com.ecoheat.data.remote

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/users/register")
    suspend fun registerUser(@Body name: String, password: String): ResponseBody
}