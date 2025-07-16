package com.rbc.openweatherapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rbc.openweatherapi.models.Coordinates
import com.rbc.openweatherapi.models.WeatherSnapshotInfo
import com.rbc.openweatherapi.ui.interfaces.ISharedPrefsCoordinator
import com.rbc.openweatherapi.util.SharedPreferencesProvider

abstract class AbstractOwmBaseActivity: AppCompatActivity(), ISharedPrefsCoordinator {

    var sharedPrefs: SharedPreferencesProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = SharedPreferencesProvider(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPrefs = null
    }

    override fun getCoordinates(): Coordinates? {
        return sharedPrefs?.getLocationCoordinates()
    }

    override fun setCoordinates(coordinates: Coordinates?) {
        sharedPrefs?.setLocationCoordinates(coordinates)
    }

    override fun getDetailedDataMap(): HashMap<String, WeatherSnapshotInfo>? {
        return sharedPrefs?.getDetailedDataMap()
    }

    override fun setDetailedDataMap(dataMap: HashMap<String, WeatherSnapshotInfo>) {
        sharedPrefs?.setDetailedDataMap(dataMap)
    }

    override fun getThreeHourlyList(): List<WeatherSnapshotInfo>? =
        sharedPrefs?.getThreeHourlyList()

    override fun setThreeHourlyList(list: List<WeatherSnapshotInfo>?) {
        sharedPrefs?.setThreeHourlyList(list)
    }

    override fun clearSharedPrefs() {
        sharedPrefs?.clearSharedPrefs()
    }
}