package com.example.androiddevchallenge

import com.example.androiddevchallenge.model.WeatherInfo
import com.example.androiddevchallenge.model.toWeatherInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(city: String): WeatherInfo {
        return api.getWeather(city).toWeatherInfo()
    }
}
