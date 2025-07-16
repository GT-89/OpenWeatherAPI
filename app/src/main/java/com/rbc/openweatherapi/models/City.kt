package com.rbc.openweatherapi.models


data class City(
    var id: Long,
    var name: String,
    var coord: Coordinates,
    var country: String,
    var sunrise: Long? = null,
    var sunset: Long? = null
)
