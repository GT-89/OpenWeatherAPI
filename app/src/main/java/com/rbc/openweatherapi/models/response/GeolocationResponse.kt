package com.rbc.openweatherapi.models.response

/**
 * Data class to handle json response for geolocation data
 */
data class GeolocationResponse(
    var zip: Int,
    var name: String,
    var lat: Double,
    var lon: Double,
    var country: String
)
