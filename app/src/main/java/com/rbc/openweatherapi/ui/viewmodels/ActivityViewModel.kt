package com.rbc.openweatherapi.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rbc.openweatherapi.R
import com.rbc.openweatherapi.models.statemachine.ActivityEvents
import com.rbc.openweatherapi.models.statemachine.ActivityStates
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    private val _activityStates = MutableLiveData<ActivityStates>()
    val activityStates: MutableLiveData<ActivityStates>
        get() = _activityStates

    init {
        activityStates.postValue(ActivityStates.FragmentHomeScreenDisplayed)
    }

    fun handleEvents(event: ActivityEvents) {
        when(event) {
            is ActivityEvents.LoadHomeScreenFragment    ->  _activityStates.postValue(ActivityStates.FragmentHomeScreenDisplayed)
            is ActivityEvents.LoadDetailsScreenFragment ->  _activityStates.postValue(ActivityStates.FragmentDetailsScreenDisplayed(R.id.homeScreenFragment_to_detailsScreenFragment))
        }
    }

}