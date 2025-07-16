package com.rbc.openweatherapi.network.requests

import com.rbc.openweatherapi.models.response.GeolocationResponse
import com.rbc.openweatherapi.network.ApiClient
import com.rbc.openweatherapi.network.NetworkConstants
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GeolocationDataRequest {

    /**
     * suspended function in charge of retrieving geolocation data using
     * the OpenWeatherMapApi. A paid API Key/subscription is necessary to utilize this call
     *
     * @param zipCode String zip code/ postal code
     * @param countryCode String country code
     * @return GeolocationResponse?
     */
    suspend fun retrieveGeocodeData(zipCode: String, countryCode: String): GeolocationResponse? {
        val result = CompletableDeferred<GeolocationResponse?>()

        withContext(Dispatchers.IO) {
            ApiClient
                .openWeatherMapApiRetrofitService
                .getGeocodeDataByZipCode(zipCode, countryCode, NetworkConstants.OPEN_WEATHER_PAID_API_KEY)
                .enqueue(object : Callback<GeolocationResponse> {
                    override fun onResponse(
                        call: Call<GeolocationResponse>,
                        response: Response<GeolocationResponse>
                    ) {
                        response.body()?.let {
                            result.complete(it)
                        }
                    }

                    override fun onFailure(call: Call<GeolocationResponse>, t: Throwable) {
                        result.complete(null)
                    }
                })
        }

        return result.await()
    }

}