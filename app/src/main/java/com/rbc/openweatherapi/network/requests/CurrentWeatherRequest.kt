package com.rbc.openweatherapi.network.requests

import com.rbc.openweatherapi.models.response.CurrentWeatherResponse
import com.rbc.openweatherapi.network.ApiClient
import com.rbc.openweatherapi.network.NetworkConstants
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentWeatherRequest {

    /**
     * suspended function in charge of retrieving current weather data using
     * the OpenWeatherMapApi
     *
     * @param lat double latitude
     * @param lon double longitude
     * @return CurrentWeatherResponse?
     */
    suspend fun retrieveCurrentWeather(lat: Double, lon: Double): CurrentWeatherResponse? {
        val result = CompletableDeferred<CurrentWeatherResponse?>()

        withContext(Dispatchers.IO) {
            ApiClient
                .openWeatherMapApiRetrofitService
                .getCurrentWeather(lat, lon, "metric", NetworkConstants.OPEN_WEATHER_FREE_API_KEY)
                .enqueue(object: Callback<CurrentWeatherResponse> {
                    override fun onResponse(
                        call: Call<CurrentWeatherResponse>,
                        response: Response<CurrentWeatherResponse>
                    ) {
                        response.body()?.let {
                            result.complete(it)
                        }
                    }

                    override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                        result.complete(null)
                    }
                })
        }

        return result.await()
    }

}