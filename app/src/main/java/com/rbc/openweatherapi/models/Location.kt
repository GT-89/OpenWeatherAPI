package com.rbc.openweatherapi.models

data class Location(
    var id: Long? = null,
    var zip: String,
    var name: String,
    var lat: Double,
    var lon: Double,
    var country: String
)
