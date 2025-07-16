package com.rbc.openweatherapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rbc.openweatherapi.databinding.ActivityMainBinding
import com.rbc.openweatherapi.models.Coordinates
import com.rbc.openweatherapi.models.statemachine.ActivityEvents
import com.rbc.openweatherapi.models.statemachine.ActivityStates
import com.rbc.openweatherapi.ui.viewmodels.ActivityViewModel
import javax.inject.Inject

class MainActivity : AbstractOwmNavigationActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pbLoading: ProgressBar
    private lateinit var toolbar: Toolbar
    @Inject lateinit var activityViewModel: ActivityViewModel

    private var currentCoordinates: Coordinates? = null
    private var isDetailScreenDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        activityViewModel = ViewModelProvider(this)[ActivityViewModel::class.java]
        currentCoordinates = getCoordinates()

        initViews()
        observeStates()
    }

    private fun initViews() {
        pbLoading = binding.pbLoading
        toolbar = binding.abToolbar.findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        setHomeAsUpEnabled(true)
        setupNavigation()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun observeStates() {
        activityViewModel.activityStates.observe(this) {
            when(it) {
                is ActivityStates.FragmentHomeScreenDisplayed       ->  isDetailScreenDisplayed = false
                is ActivityStates.FragmentDetailsScreenDisplayed    ->  checkShouldDisplayDetailedScreen(it.navActionResId)
            }
        }
    }

    private fun checkShouldDisplayDetailedScreen(navActionResId: Int) {
        if(!isDetailScreenDisplayed) {
            isDetailScreenDisplayed = true
            navigate(navActionResId)
        } else {
            isDetailScreenDisplayed = false
        }
    }

    override fun showNetworkProgressBar(isVisible: Boolean) {
        binding.pbLoading.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
    }

    fun displayDetailedFragment() {
        activityViewModel.handleEvents(ActivityEvents.LoadDetailsScreenFragment)
    }

    override fun onDestroy() {
        clearSharedPrefs()
        super.onDestroy()
    }
}