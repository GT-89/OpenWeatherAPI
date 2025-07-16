package com.rbc.openweatherapi.network

import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton APiClient object responsible for handling network API requests
 * to the OpenWeatherMapAPI using retrofit + okhttp3. currently using GSON to parse
 */
object ApiClient {

    private val gson by lazy {
        GsonBuilder()
            .setStrictness(Strictness.LENIENT)
            .create()
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .build()
    }

    private val openWeatherMapRetrofitObject: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.OPEN_WEATHER_MAP_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val openWeatherMapApiRetrofitService: IOpenWeatherMapApi by lazy {
        openWeatherMapRetrofitObject.create(IOpenWeatherMapApi::class.java)
    }

}