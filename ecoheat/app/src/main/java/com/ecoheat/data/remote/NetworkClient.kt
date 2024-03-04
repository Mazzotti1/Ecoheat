package com.ecoheat.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.github.cdimascio.dotenv.dotenv

object NetworkClient {
    private val dotenv = dotenv {
        directory = "/assets"
        filename = "env"
    }

    private val baseUrl = dotenv["BASE_URL"]!!

    fun create(authtoken:String?= ""): ApiService {


        val okHttpClient = authtoken?.let { AuthInterceptor(it) }?.let {
            OkHttpClient.Builder()
                .addInterceptor(it)
                .build()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }

}