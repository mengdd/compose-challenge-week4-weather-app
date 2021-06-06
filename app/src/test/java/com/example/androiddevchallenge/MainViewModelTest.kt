package com.example.androiddevchallenge

import com.example.androiddevchallenge.model.WeatherInfo
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val repository: WeatherRepository = mockk()
    private val viewModel = MainViewModel(repository)

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun givenTemperature40_whenGetWeather_stateShow40() {

        coEvery { repository.getWeather(any()) } returns WeatherInfo(
            temperature = "40",
            wind = "no",
            description = "hot"
        )

        viewModel.getWeather()

        assertThat(viewModel.state.value.temperature).isEqualTo("40")
    }
}