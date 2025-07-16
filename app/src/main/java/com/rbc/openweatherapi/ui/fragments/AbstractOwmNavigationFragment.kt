package com.rbc.openweatherapi.ui.fragments

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.rbc.openweatherapi.AbstractOwmNavigationActivity
import com.rbc.openweatherapi.R

abstract class AbstractOwmNavigationFragment: AbstractOwmPermissionsFragment() {

    protected lateinit var navController: NavController
    private var windowActivity: AbstractOwmNavigationActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is AbstractOwmNavigationActivity) {
            windowActivity = context
        }
    }

    protected fun setupNavigation() {
        windowActivity?.let {
            val navHostFragment = it.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
            navController.setGraph(R.navigation.nav_graph)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}