package com.rbc.openweatherapi.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbc.openweatherapi.models.WeatherSnapshotInfo
import com.rbc.openweatherapi.models.response.CurrentWeatherResponse
import com.rbc.openweatherapi.models.response.FiveDayThreeHourForecastResponse
import com.rbc.openweatherapi.models.response.HourlyForecastResponse
import com.rbc.openweatherapi.models.statemachine.DetailsScreenStates
import com.rbc.openweatherapi.models.statemachine.HomeScreenStates

abstract class AbstractWeatherViewModel: ViewModel() {

    protected val _homeScreenStates = MutableLiveData<HomeScreenStates>()
    val homeScreenStates: MutableLiveData<HomeScreenStates>
        get() = _homeScreenStates

    protected val _detailsScreenStates = MutableLiveData<DetailsScreenStates>()
    val detailsScreenStates: MutableLiveData<DetailsScreenStates>
        get() = _detailsScreenStates

    protected val _currentWeatherData = MutableLiveData<CurrentWeatherResponse>()
    val currentWeatherData: MutableLiveData<CurrentWeatherResponse>
        get() = _currentWeatherData

    protected val _fiveDayForecastData = MutableLiveData<FiveDayThreeHourForecastResponse>()
    val fiveDayForecastData: MutableLiveData<FiveDayThreeHourForecastResponse>
        get() = _fiveDayForecastData

    protected val _detailedForecastData = MutableLiveData<HashMap<String, WeatherSnapshotInfo>>()
    val detailedForecastData: MutableLiveData<HashMap<String, WeatherSnapshotInfo>>
        get() = _detailedForecastData

    protected val _hourlyForecastData = MutableLiveData<HourlyForecastResponse>()
    val hourlyForecastData: MutableLiveData<HourlyForecastResponse>
        get() = _hourlyForecastData

    protected val _homeScreenDataRetrieved = MutableLiveData<Boolean>()
    val homeScreenDataRetrieved: MutableLiveData<Boolean>
        get() = _homeScreenDataRetrieved

    abstract fun <T> handleEvent(event: T)

}