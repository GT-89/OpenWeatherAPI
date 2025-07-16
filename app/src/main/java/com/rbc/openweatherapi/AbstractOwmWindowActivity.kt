package com.rbc.openweatherapi

import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.rbc.openweatherapi.ui.interfaces.IWindowComponent

abstract class AbstractOwmWindowActivity: AbstractOwmBaseActivity(), IWindowComponent {

    override fun showSoftKeyboard() {
        val view = currentFocus
        if (view != null) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInputFromInputMethod(view.windowToken, 0)
        }
    }

    override fun hideSoftKeyboard() {
        val view = window.decorView.rootView
        if (view != null) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun setHomeAsUpEnabled(isEnabled: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isEnabled)
    }

    override fun showToast(message: String) {
        Toast
            .makeText(applicationContext, message, Toast.LENGTH_LONG)
            .show()
    }

}