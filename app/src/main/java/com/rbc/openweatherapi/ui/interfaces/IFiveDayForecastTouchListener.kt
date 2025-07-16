package com.rbc.openweatherapi.ui.interfaces

import com.rbc.openweatherapi.models.WeatherSnapshotInfo

interface IFiveDayForecastTouchListener {
    fun onDailyItemClicked(item: WeatherSnapshotInfo)
}