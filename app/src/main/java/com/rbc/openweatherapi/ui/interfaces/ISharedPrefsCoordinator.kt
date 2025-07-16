package com.rbc.openweatherapi.ui.interfaces

import com.rbc.openweatherapi.models.Coordinates
import com.rbc.openweatherapi.models.WeatherSnapshotInfo
import java.util.HashMap

interface ISharedPrefsCoordinator {
    fun getCoordinates(): Coordinates?
    fun setCoordinates(coordinates: Coordinates?)
    fun getDetailedDataMap(): HashMap<String, WeatherSnapshotInfo>?
    fun setDetailedDataMap(dataMap: HashMap<String, WeatherSnapshotInfo>)
    fun getThreeHourlyList(): List<WeatherSnapshotInfo>?
    fun setThreeHourlyList(list: List<WeatherSnapshotInfo>?)
    fun clearSharedPrefs()
}