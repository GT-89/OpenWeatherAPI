package com.rbc.openweatherapi.models.statemachine

import android.os.Bundle

sealed class ActivityStates {
    data object FragmentHomeScreenDisplayed: ActivityStates()
    data class FragmentDetailsScreenDisplayed(val navActionResId: Int): ActivityStates()
}