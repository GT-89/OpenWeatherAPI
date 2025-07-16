package com.rbc.openweatherapi.models

import android.Manifest.permission.*

sealed class Permission(vararg val permissions: String) {
    // Individual permissions
    data object Camera: Permission(CAMERA)

    // Bundled permissions
    data object CameraFeature: Permission(CAMERA, WRITE_EXTERNAL_STORAGE)

    // Grouped permissions
    data object Location: Permission(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
    data object Storage: Permission(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
    data object Network: Permission(INTERNET, ACCESS_NETWORK_STATE, ACCESS_WIFI_STATE)
    data object Audio: Permission(RECORD_AUDIO, MODIFY_AUDIO_SETTINGS)

    companion object {
        fun from(permission: String) = when(permission) {
            ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION        ->  Location
            WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE       ->  Storage
            INTERNET, ACCESS_NETWORK_STATE, ACCESS_WIFI_STATE   ->  Network
            RECORD_AUDIO, MODIFY_AUDIO_SETTINGS                 ->  Audio
            CAMERA, WRITE_EXTERNAL_STORAGE                      ->  CameraFeature
            CAMERA                                              ->  Camera
            else                                                ->  throw IllegalArgumentException("Unknown Permission: $permission")
        }
    }
}