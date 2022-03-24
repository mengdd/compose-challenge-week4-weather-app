package com.example.androiddevchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val isLoading: Boolean = true,
    val weatherResponse: WeatherResponse? = null,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        getWeather()
    }

    fun getWeather() {
        _state.value = _state.value.copy(isLoading = true)
        updateState { copy(isLoading = true) }
        viewModelScope.launch {
            val result = repository.getWeather("Xi'an")
            updateState { copy(isLoading = false, weatherResponse = result) }
        }
    }

    fun updateState(block: UiState.() -> UiState) {
        _state.value = _state.value.block()
    }
}