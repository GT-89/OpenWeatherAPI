package com.rbc.openweatherapi.models.response

import com.rbc.openweatherapi.models.Clouds
import com.rbc.openweatherapi.models.Rain
import com.rbc.openweatherapi.models.SysInfo
import com.rbc.openweatherapi.models.WeatherDescription
import com.rbc.openweatherapi.models.WeatherMainInfo
import com.rbc.openweatherapi.models.Wind

/**
 * Data class to handle json response for current weather
 */
data class CurrentWeatherResponse(
    var id: Long,
    var name: String,
    var weather: List<WeatherDescription>,
    var main: WeatherMainInfo,
    var visibility: Int,
    var wind: Wind,
    var rain: Rain,
    var clouds: Clouds,
    var dt: Long,
    var sys: SysInfo,
)
