package com.example.androiddevchallenge

import com.example.androiddevchallenge.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {
    @GET("{city}")
    suspend fun getWeather(@Path("city") city: String): WeatherResponse
}