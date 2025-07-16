package com.rbc.openweatherapi.models

data class DailyForecastInfo(
    var dt: Long,
    var sunrise: Long,
    var sunset: Long,
    var temp: TemperatureInfo,
    var feels_like: FeelsLikeInfo,
    var pressure: Int,
    var humidity: Int,
    var weather: WeatherDescription,
    var speed: Double,
    var deg: Int,
    var gust: Double,
    var clouds: Int,
    var pop: Double,
    var rain: Double
)
