package com.ecoheat.data.repository

import com.ecoheat.Model.Login
import com.ecoheat.Model.Register
import com.ecoheat.data.remote.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LoginRepository {
    //caso precise tem que por o token como string na instancia da api
    private val apiService = NetworkClient.create()

    suspend fun loginUser(request: Login): String {
        return try {
            withContext(Dispatchers.IO) {
                apiService.loginUser(request)
            }
            "Login bem sucedido"

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            errorBody ?: "HttpException"

        } catch (e: Throwable) {
            println(e)
            "Throwable"
        }
    }
}