package com.rbc.openweatherapi.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rbc.openweatherapi.models.Coordinates
import com.rbc.openweatherapi.models.WeatherSnapshotInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton

private const val LOCATION_COORDINATES = "LOCATION_COORDINATES"
private const val DETAILED_FORECAST_DATA_MAP = "DETAILED_FORECAST_DATA_MAP"
private const val THREE_HOURLY_LIST = "THREE_HOURLY_LIST"

@Singleton
class SharedPreferencesProvider @Inject constructor(@ApplicationContext context: Context) {

    private val prefsName = "OPEN_WEATHER_SHARED_PREFS"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun getLocationCoordinates(): Coordinates? {
        val jsonCoordinates = prefs.getString(LOCATION_COORDINATES, null)
        return Gson().fromJson(jsonCoordinates, Coordinates::class.java)
    }

    fun setLocationCoordinates(coordinates: Coordinates?) {
        val jsonCoordinates = Gson().toJson(coordinates)
        prefs.edit {
            putString(LOCATION_COORDINATES, if(coordinates == null) null else jsonCoordinates)
            apply()
        }
    }

    fun getDetailedDataMap(): HashMap<String, WeatherSnapshotInfo>? {
        val jsonDataMap = prefs.getString(DETAILED_FORECAST_DATA_MAP, null)
        val type = object : TypeToken<HashMap<String, WeatherSnapshotInfo>>() {}.type
        return Gson().fromJson(jsonDataMap, type)
    }

    fun setDetailedDataMap(dataMap: HashMap<String, WeatherSnapshotInfo>) {
        val jsonDataMap = Gson().toJson(dataMap)
        prefs.edit {
            putString(DETAILED_FORECAST_DATA_MAP, jsonDataMap)
            apply()
        }
    }

    fun getThreeHourlyList(): List<WeatherSnapshotInfo>? {
        val jsonList = prefs.getString(THREE_HOURLY_LIST, null)
        val myType = object : TypeToken<List<WeatherSnapshotInfo>>() {}.type
        return Gson().fromJson(jsonList, myType)
    }

    fun setThreeHourlyList(list: List<WeatherSnapshotInfo>?) {
        val jsonList = Gson().toJson(list)
        prefs.edit {
            putString(THREE_HOURLY_LIST, jsonList)
            apply()
        }
    }

    fun clearSharedPrefs() {
        prefs.edit {
            remove(LOCATION_COORDINATES)
            remove(DETAILED_FORECAST_DATA_MAP)
            clear()
            commit()
        }
    }

}