package com.rbc.openweatherapi.models.response

import com.rbc.openweatherapi.models.City
import com.rbc.openweatherapi.models.DailyForecastInfo

/**
 * Data class to handle json response for daily forecast
 */
data class DailyForecastResponse(
    var city: City,
    var list: List<DailyForecastInfo>
)
