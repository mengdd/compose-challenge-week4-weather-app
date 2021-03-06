package com.example.androiddevchallenge

import com.example.androiddevchallenge.model.WeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(city: String): WeatherResponse? {
        return kotlin.runCatching {
            api.getWeather(city)
        }.getOrNull()
    }
}
