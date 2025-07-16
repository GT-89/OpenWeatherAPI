package com.rbc.openweatherapi.models.response

import com.rbc.openweatherapi.models.City
import com.rbc.openweatherapi.models.WeatherSnapshotInfo

/**
 * Data class to handle json response for hourly forecast
 */
data class HourlyForecastResponse(
    var city: City,
    var list: List<WeatherSnapshotInfo>
)
