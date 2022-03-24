/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.androiddevchallenge.model.Forecast
import com.example.androiddevchallenge.model.WeatherResponse
import com.example.androiddevchallenge.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                val state = viewModel.state.collectAsState()
                MyApp(
                    state = state.value
                ) {
                    viewModel.getWeather()
                }
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(state: UiState = UiState(), refreshAction: () -> Unit = {}) {
    Surface(color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                if (state.weatherResponse != null) {
                    Column {
                        TodayWeather(state.weatherResponse)
                        LazyColumn {
                            items(state.weatherResponse.forecast) {
                                ForecastItem(it)
                            }
                        }
                    }
                } else {
                    Text(text = stringResource(R.string.error_message))
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                onClick = refreshAction
            ) {
                Text(text = stringResource(R.string.refresh))
            }
        }

    }
}

@Composable
fun TodayWeather(weatherResponse: WeatherResponse) {
    Card(modifier = Modifier.padding(16.dp).height(200.dp)) {
        Image(
            painter = rememberImagePainter("https://images.unsplash.com/photo-1533324268742-60b233802eef?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.wrapContentWidth(),
                text = stringResource(R.string.today),
                style = MaterialTheme.typography.h6
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = weatherResponse.temperature,
                    style = MaterialTheme.typography.h4
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(R.string.wind, weatherResponse.wind),
                    style = MaterialTheme.typography.h5
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = weatherResponse.description,
                    style = MaterialTheme.typography.h6
                )
            }
        }

    }


}

@Composable
fun ForecastItem(forecast: Forecast) {
    Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.WbSunny, contentDescription = "Localized description")
            Text(
                modifier = Modifier.padding(16.dp).wrapContentWidth(),
                text = getDayString(forecast.day),
                style = MaterialTheme.typography.h6
            )
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = forecast.temperature,
                    style = MaterialTheme.typography.h4
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(R.string.wind, forecast.wind),
                    style = MaterialTheme.typography.h5
                )
            }

        }

    }

}

@Composable
fun getDayString(day: Int): String {
    val resources = LocalContext.current.resources
    return when (day) {
        1 -> resources.getString(R.string.tomorrow)
        2 -> resources.getString(R.string.days_later, day)
        3 -> resources.getString(R.string.days_later, day)
        else -> resources.getString(R.string.future)
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
