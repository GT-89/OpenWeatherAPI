package com.rbc.openweatherapi.ui.fragments

import android.location.Location
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.rbc.openweatherapi.R
import com.rbc.openweatherapi.models.Coordinates
import com.rbc.openweatherapi.models.Permission
import com.rbc.openweatherapi.ui.interfaces.IPermissionsHandler
import com.rbc.openweatherapi.util.PermissionManager

abstract class AbstractOwmPermissionsFragment: AbstractOwmWindowFragment(), IPermissionsHandler {

    private var permissionManager = PermissionManager.from(this)
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun checkPermissionsAndRetrieveGeolocationData(callback: () -> Unit){
        permissionManager
            .request(Permission.Location)
            .rationale(resources.getString(R.string.permission_rationale_location))
            .checkPermission { granted ->
                if(granted) {
                    callback.invoke()
                }
            }
    }

    @Suppress("MissingPermission")
    override fun getCurrentLocationIfLastLocationIsNull(callback: () -> Unit) {
        fusedLocationClient
            .lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    setCoordinates(Coordinates(location.latitude, location.longitude))
                    callback.invoke()
                } ?: kotlin.run {
                    fusedLocationClient
                        .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                        .addOnSuccessListener { location: Location? ->
                            location?.let {
                                setCoordinates(Coordinates(location.latitude, location.longitude))
                                callback.invoke()
                            }
                        }
                        .addOnFailureListener { exception ->
                            //TODO: handle better
                            showToast(exception.message.toString())
                        }
                }
            }
            .addOnFailureListener { exception ->
                //TODO: handle better
                showToast(exception.message.toString())
            }
    }
}