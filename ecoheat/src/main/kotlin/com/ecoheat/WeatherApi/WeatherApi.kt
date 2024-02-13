package com.ecoheat.WeatherApi
import com.ecoheat.Service.IWeatherService
import io.github.cdimascio.dotenv.dotenv
import okhttp3.*
import java.io.IOException


class WeatherApi {
    fun getWeatherJson(q: String?, days: Int?, hour: Int?, lang: String?, callback: IWeatherService) {

        val dotenv = dotenv()
        val apiKey = dotenv["WEATHER_API_KEY"]!!
        val client = OkHttpClient()

        val url = "https://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$q&days=$days&hour=$hour&lang=$lang"
        val request = Request.Builder()
            .url(url)
            .header("x-api-key", apiKey)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    callback.onWeatherResponse(responseBody)
                } else {
                    callback.onWeatherFailure("Failed to get weather data")
                }
            }
        })
    }
}
