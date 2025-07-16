package com.rbc.openweatherapi.models.response

import com.rbc.openweatherapi.models.City
import com.rbc.openweatherapi.models.WeatherSnapshotInfo

/**
 * Data class to handle json response for 5 day 3 hour weather forecast
 */
data class FiveDayThreeHourForecastResponse (
    var city: City,
    var list: List<WeatherSnapshotInfo>
)