package com.rbc.openweatherapi.models.statemachine

sealed class ActivityEvents {
    data object LoadHomeScreenFragment: ActivityEvents()
    data object LoadDetailsScreenFragment: ActivityEvents()
}