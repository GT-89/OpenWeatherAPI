package com.rbc.openweatherapi.network

import com.rbc.openweatherapi.models.response.CurrentWeatherResponse
import com.rbc.openweatherapi.models.response.DailyForecastResponse
import com.rbc.openweatherapi.models.response.FiveDayThreeHourForecastResponse
import com.rbc.openweatherapi.models.response.GeolocationResponse
import com.rbc.openweatherapi.models.response.HourlyForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * OpenWeatherMap API interface utilized by retrofit to make specific
 * network API GET requests
 */
interface IOpenWeatherMapApi {

    @GET("data/${NetworkConstants.OPEN_WEATHER_VERSION_2_5_0}/weather")
    fun getCurrentWeather(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("units") units: String, @Query("appid") apiKey: String): Call<CurrentWeatherResponse>

    @GET("data/${NetworkConstants.OPEN_WEATHER_VERSION_2_5_0}/forecast?")
    fun getFiveDayThreeHourForecastData(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("units") units: String, @Query("appid") apiKey: String): Call<FiveDayThreeHourForecastResponse>

    // the following calls can only be made with a paid subscription API Key

    // country code must be in ISO 3166 form
    @GET("/geo/${NetworkConstants.GEO_VERSION}/zip")
    fun getGeocodeDataByZipCode(@Query("zip") zip: String, @Query("countryCode") countryCode: String, @Query("appid") apiKey: String): Call<GeolocationResponse>

    @GET("data/${NetworkConstants.OPEN_WEATHER_VERSION_2_5_0}/forecast/daily")
    fun getDailyForecast(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("cnt") days: Int, @Query("units") units: String, @Query("appid") apiKey: String): Call<DailyForecastResponse>

    @GET("/data/${NetworkConstants.OPEN_WEATHER_VERSION_2_5_0}/forecast/hourly")
    fun getHourlyForecastInfo(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("units") units: String, @Query("appid") apiKey: String): Call<HourlyForecastResponse>

}