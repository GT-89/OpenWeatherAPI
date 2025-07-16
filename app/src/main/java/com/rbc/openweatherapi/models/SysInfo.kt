package com.rbc.openweatherapi.models

data class SysInfo(
    var id: Long? = null,
    var pod: String? = null,
    var sunrise: Long? = null,
    var sunset: Long? = null
)
