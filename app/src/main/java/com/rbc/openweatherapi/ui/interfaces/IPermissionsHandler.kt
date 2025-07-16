package com.rbc.openweatherapi.ui.interfaces

interface IPermissionsHandler {
    fun checkPermissionsAndRetrieveGeolocationData(callback: () -> Unit)
    fun getCurrentLocationIfLastLocationIsNull(callback: () -> Unit)
}