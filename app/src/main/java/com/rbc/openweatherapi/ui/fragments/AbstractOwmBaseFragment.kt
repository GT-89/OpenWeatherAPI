package com.rbc.openweatherapi.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.rbc.openweatherapi.AbstractOwmBaseActivity
import com.rbc.openweatherapi.models.Coordinates
import com.rbc.openweatherapi.models.WeatherSnapshotInfo
import com.rbc.openweatherapi.ui.interfaces.IPermissionsHandler
import com.rbc.openweatherapi.ui.interfaces.ISharedPrefsCoordinator
import com.rbc.openweatherapi.util.SharedPreferencesProvider
import java.util.HashMap

abstract class AbstractOwmBaseFragment: Fragment(), ISharedPrefsCoordinator, IPermissionsHandler {

    protected var sharedPrefs: SharedPreferencesProvider? = null
    private var windowActivity: AbstractOwmBaseActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = requireContext()
        if(context is AbstractOwmBaseActivity) {
            sharedPrefs = context.sharedPrefs
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPrefs = null
    }

    override fun setCoordinates(coordinates: Coordinates?) {
        sharedPrefs?.setLocationCoordinates(coordinates)
    }

    override fun getCoordinates(): Coordinates? =
        sharedPrefs?.getLocationCoordinates()

    override fun getDetailedDataMap(): HashMap<String, WeatherSnapshotInfo>? =
        sharedPrefs?.getDetailedDataMap()

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