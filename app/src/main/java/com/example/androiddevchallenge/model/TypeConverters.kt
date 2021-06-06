package com.example.androiddevchallenge.model

fun WeatherResponse.toWeatherInfo() = WeatherInfo(
    temperature = this.temperature,
    wind = this.wind,
    description = this.description
)