package com.rbc.openweatherapi.models.statemachine

sealed class HomeScreenStates {
    data object InitialState: HomeScreenStates()
    data object DailyForecastSelected: HomeScreenStates()
}