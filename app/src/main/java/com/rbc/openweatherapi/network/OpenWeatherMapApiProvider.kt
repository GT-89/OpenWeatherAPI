package com.rbc.openweatherapi.network

import com.rbc.openweatherapi.models.response.CurrentWeatherResponse
import com.rbc.openweatherapi.models.response.DailyForecastResponse
import com.rbc.openweatherapi.models.response.FiveDayThreeHourForecastResponse
import com.rbc.openweatherapi.network.requests.CurrentWeatherRequest
import com.rbc.openweatherapi.network.requests.DailyForecastDataRequest
import com.rbc.openweatherapi.network.requests.FiveDayThreeHourForecastRequest

/**
 * Provider class responsible for delegating all necessary network calls
 * into on class
 */
class OpenWeatherMapApiProvider {

    suspend fun retrieveCurrentWeatherData(lat: Double,
                                           lon: Double): CurrentWeatherResponse? =
        CurrentWeatherRequest().retrieveCurrentWeather(lat, lon)

    suspend fun retrieve5day3hourForecastData(lat: Double,
                                              lon: Double): FiveDayThreeHourForecastResponse? =
        FiveDayThreeHourForecastRequest().retrieve5day3hourForecastData(lat, lon)

    suspend fun retrieveUpcomingWeekWeatherForecastData(
        lat: Double,
        lon: Double,
        days: Int
    ): DailyForecastResponse? =
        DailyForecastDataRequest().retrieveDailyForecastData(lat, lon, days)

}