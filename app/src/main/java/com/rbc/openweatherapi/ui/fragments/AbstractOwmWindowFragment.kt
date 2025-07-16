package com.rbc.openweatherapi.ui.fragments

import android.content.Context
import com.rbc.openweatherapi.AbstractOwmWindowActivity
import com.rbc.openweatherapi.ui.interfaces.IWindowComponent

abstract class AbstractOwmWindowFragment: AbstractOwmBaseFragment(), IWindowComponent {

    private var windowActivity: AbstractOwmWindowActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AbstractOwmWindowActivity) {
            windowActivity = context
        }
    }

    override fun showSoftKeyboard() {
        windowActivity?.showSoftKeyboard()
    }

    override fun hideSoftKeyboard() {
        windowActivity?.hideSoftKeyboard()
    }

    override fun setHomeAsUpEnabled(isEnabled: Boolean) {
        windowActivity?.setHomeAsUpEnabled(isEnabled)
    }

    override fun showToast(message: String) {
        windowActivity?.showToast(message)
    }

    override fun showNetworkProgressBar(isVisible: Boolean) {
        windowActivity?.showNetworkProgressBar(isVisible)
    }
}