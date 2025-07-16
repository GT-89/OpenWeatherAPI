package com.rbc.openweatherapi.models

data class WeatherDescription(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)
