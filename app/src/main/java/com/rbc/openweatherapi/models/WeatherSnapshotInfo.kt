package com.rbc.openweatherapi.models

data class WeatherSnapshotInfo(
    var id: Long? = null,
    var dt: Long,
    var dt_txt: String,
    var main: WeatherMainInfo,
    var weather: List<WeatherDescription>,
    var clouds: Clouds,
    var wind: Wind,
    var visibility: Int,
    var pop: Double,
    var rain: Rain,
    var sys: SysInfo
)
