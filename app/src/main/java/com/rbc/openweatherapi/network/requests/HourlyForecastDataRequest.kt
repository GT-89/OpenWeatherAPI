package com.rbc.openweatherapi.network.requests

import com.rbc.openweatherapi.models.response.HourlyForecastResponse
import com.rbc.openweatherapi.network.ApiClient
import com.rbc.openweatherapi.network.NetworkConstants
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HourlyForecastDataRequest {

    /**
     * suspended function in charge of retrieving daily forecast weather data using
     * the OpenWeatherMapApi. A paid API Key/subscription is necessary to utilize this call
     *
     * @param lat double latitude
     * @param lon double longitude
     * @return HourlyForecastResponse?
     */
    suspend fun retrieveHourlyForecast(lat: Double, lon: Double): HourlyForecastResponse? {
        val result = CompletableDeferred<HourlyForecastResponse?>()

        withContext(Dispatchers.IO) {
            ApiClient
                .openWeatherMapApiRetrofitService
                .getHourlyForecastInfo(lat, lon, "metric", NetworkConstants.OPEN_WEATHER_PAID_API_KEY)
                .enqueue(object: Callback<HourlyForecastResponse> {
                    override fun onResponse(
                        call: Call<HourlyForecastResponse>,
                        response: Response<HourlyForecastResponse>
                    ) {
                        response.body()?.let {
                            result.complete(it)
                        }
                    }

                    override fun onFailure(call: Call<HourlyForecastResponse>, t: Throwable) {
                        result.complete(null)
                    }
                })
        }

        return result.await()
    }

}