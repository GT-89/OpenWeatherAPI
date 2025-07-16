package com.rbc.openweatherapi.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.rbc.openweatherapi.models.statemachine.HomeScreenEvents
import com.rbc.openweatherapi.models.statemachine.HomeScreenStates
import com.rbc.openweatherapi.network.OpenWeatherMapApiProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel: AbstractWeatherViewModel() {


    init {
        _homeScreenStates.postValue(HomeScreenStates.InitialState)
    }

    override fun <T> handleEvent(event: T) {
        when(event) {
            is HomeScreenEvents.RetrieveWeatherData             ->  retrieveWeatherData(event.lat, event.lon, event.days)
            is HomeScreenEvents.DisplayDetailedWeatherForecast  ->  displayDetailedForecast()
        }
    }

    private fun displayDetailedForecast() {
        _homeScreenStates.postValue(HomeScreenStates.DailyForecastSelected)
    }

    private fun retrieveWeatherData(lat: Double, lon: Double, days: Int) {
        retrieveCurrentWeatherData(lat, lon)
        retrieve5dayForecastData(lat, lon, days)
    }

    private fun retrieveCurrentWeatherData(lat: Double, lon: Double) {
        viewModelScope.launch {
            val currentWeatherData = async {
                OpenWeatherMapApiProvider().retrieveCurrentWeatherData(lat, lon)
            }.await()

            _currentWeatherData.postValue(currentWeatherData)
            if(_fiveDayForecastData.value != null) {
                _homeScreenDataRetrieved.postValue(true)
            }
        }
    }

    private fun retrieve5dayForecastData(lat: Double, lon: Double, days: Int) {
        viewModelScope.launch {
            val fiveDayForecast = async {
                OpenWeatherMapApiProvider().retrieve5day3hourForecastData(lat, lon)
            }.await()

            _fiveDayForecastData.postValue(fiveDayForecast)
            if(_currentWeatherData.value != null) {
                _homeScreenDataRetrieved.postValue(true)
            }
        }
    }

}