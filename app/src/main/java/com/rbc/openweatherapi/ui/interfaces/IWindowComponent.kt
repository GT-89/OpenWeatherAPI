package com.rbc.openweatherapi.ui.interfaces

interface IWindowComponent {
    fun showSoftKeyboard()
    fun hideSoftKeyboard()
    fun setHomeAsUpEnabled(isEnabled: Boolean)
    fun showToast(message: String)
    fun showNetworkProgressBar(isVisible: Boolean)
}