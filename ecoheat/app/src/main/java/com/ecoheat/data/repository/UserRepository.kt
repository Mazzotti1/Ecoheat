package com.ecoheat.data.repository

import com.ecoheat.data.remote.ApiService
import okhttp3.ResponseBody

class UserRepository(private val apiService:ApiService) {
    suspend fun registerUser (name: String, password: String): ResponseBody {
        return apiService.registerUser(name, password)
    }
}