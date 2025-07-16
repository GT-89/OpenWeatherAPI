package com.rbc.openweatherapi

import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class AbstractOwmNavigationActivity: AbstractOwmWindowActivity() {

    protected lateinit var navController: NavController
    private lateinit var toolbar: Toolbar

    protected fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected fun navigate(navResId: Int) {
        navController.navigate(navResId)
    }
}