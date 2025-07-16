package com.rbc.openweatherapi.models

data class TemperatureInfo(
    var day: Double,
    var min: Double,
    var max: Double,
    var night: Double,
    var eve: Double,
    var morn: Double
)
