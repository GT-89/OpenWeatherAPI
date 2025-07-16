package com.rbc.openweatherapi

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OwmApplication: Application() {

    override fun onCreate() {
        super.onCreate()

    }
}