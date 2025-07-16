package com.rbc.openweatherapi.network.requests

import com.rbc.openweatherapi.models.response.DailyForecastResponse
import com.rbc.openweatherapi.network.ApiClient
import com.rbc.openweatherapi.network.NetworkConstants
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyForecastDataRequest {

    /**
     * suspended function in charge of retrieving daily forecast weather data using
     * the OpenWeatherMapApi. A paid API Key/subscription is necessary to utilize this call
     *
     * @param lat double latitude
     * @param lon double longitude
     * @param days  Int total number of days for data to pull
     * @return DailyForecastResponse?
     */
    suspend fun retrieveDailyForecastData(lat: Double, lon: Double, days: Int): DailyForecastResponse? {
        val result = CompletableDeferred<DailyForecastResponse?>()

        withContext(Dispatchers.IO) {
            ApiClient
                .openWeatherMapApiRetrofitService
                .getDailyForecast(lat, lon, days, "metric", NetworkConstants.OPEN_WEATHER_PAID_API_KEY)
                .enqueue(object: Callback<DailyForecastResponse> {
                    override fun onResponse(
                        call: Call<DailyForecastResponse>,
                        response: Response<DailyForecastResponse>
                    ) {
                        response.body()?.let {
                            result.complete(it)
                        }
                    }

                    override fun onFailure(call: Call<DailyForecastResponse>, t: Throwable) {
                        result.complete(null)
                    }
                })
        }

        return result.await()
    }

}