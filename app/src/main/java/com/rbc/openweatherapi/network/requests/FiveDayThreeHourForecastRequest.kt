package com.rbc.openweatherapi.network.requests

import com.rbc.openweatherapi.models.response.FiveDayThreeHourForecastResponse
import com.rbc.openweatherapi.network.ApiClient
import com.rbc.openweatherapi.network.NetworkConstants
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FiveDayThreeHourForecastRequest {

    /**
     * suspended function in charge of retrieving 5-day 3-hour weather data using
     * the OpenWeatherMapApi.
     *
     * @param lat double latitude
     * @param lon double longitude
     * @return FiveDayThreeHourForecastResponse?
     */
    suspend fun retrieve5day3hourForecastData(lat: Double, lon: Double): FiveDayThreeHourForecastResponse? {
        val result = CompletableDeferred<FiveDayThreeHourForecastResponse?>()

        withContext(Dispatchers.IO) {
            ApiClient
                .openWeatherMapApiRetrofitService
                .getFiveDayThreeHourForecastData(lat, lon, "metric", NetworkConstants.OPEN_WEATHER_FREE_API_KEY)
                .enqueue(object: Callback<FiveDayThreeHourForecastResponse> {
                    override fun onResponse(
                        call: Call<FiveDayThreeHourForecastResponse>,
                        response: Response<FiveDayThreeHourForecastResponse>
                    ) {
                        response.body()?.let {
                            result.complete(it)
                        }
                    }

                    override fun onFailure(
                        call: Call<FiveDayThreeHourForecastResponse>,
                        t: Throwable
                    ) {
                        result.complete(null)
                    }
                })
        }

        return result.await()
    }

}