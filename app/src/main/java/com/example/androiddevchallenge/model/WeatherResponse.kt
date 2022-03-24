package com.example.androiddevchallenge.model


data class WeatherResponse(
    val temperature: String,
    val wind: String,
    val description: String,
    val forecast: List<Forecast>
)

data class Forecast(
    val day: Int = 0,
    val temperature: String,
    val wind: String,
)
