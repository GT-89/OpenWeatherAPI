package com.rbc.openweatherapi.models.statemachine

import com.rbc.openweatherapi.models.WeatherSnapshotInfo

sealed class HomeScreenEvents {
    data class RetrieveWeatherData(val lat: Double, val lon: Double, val days: Int): HomeScreenEvents()
    data object DisplayDetailedWeatherForecast: HomeScreenEvents()
}