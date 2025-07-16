package com.rbc.openweatherapi.util

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rbc.openweatherapi.R
import com.rbc.openweatherapi.models.Permission


class PermissionManager private constructor(private val fragment: Fragment) {

    private val requiredPermissions = mutableListOf<Permission>()
    private var rationale: String? = null
    private var callback: (Boolean) -> Unit = {}
    private var detailedCallback: (Map<Permission, Boolean>) -> Unit = {}
    private val permissionCheck =
        fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grantResults ->
            sendResultAndCleanUp(grantResults)
        }

    companion object {
        fun from(fragment: Fragment) = PermissionManager(fragment)
    }

    fun request(vararg permission: Permission): PermissionManager {
        requiredPermissions.addAll(permission)
        return this
    }

    fun rationale(description: String): PermissionManager {
        rationale = description
        return this
    }

    fun checkPermission(callback: (Boolean) -> Unit) {
        this.callback = callback
        handlePermissionRequest()
    }

    fun checkDetailedPermission(callback: (Map<Permission, Boolean>) -> Unit) {
        this.detailedCallback = callback
        handlePermissionRequest()
    }

    private fun handlePermissionRequest() {
        fragment.let { frag ->
            when {
                areAllPermissionsGranted(frag)      ->  sendPositiveResult()
                shouldShowPermissionRationale(frag) ->  displayRationale(frag)
                else                                ->  requestPermissions()
            }
        }
    }

    private fun displayRationale(fragment: Fragment) {
        MaterialAlertDialogBuilder(fragment.requireContext())
            .setTitle(fragment.requireContext().getString(R.string.permission_request_title_generic))
            .setMessage(rationale ?: fragment.requireContext().getString(R.string.permission_request_title_generic))
            .setCancelable(false)
            .setPositiveButton(fragment.requireContext().getString(R.string.okay)) { _, _ ->
                requestPermissions()
            }
            .show()
    }

    private fun sendPositiveResult() {
        sendResultAndCleanUp(getPermissionList().associateWith { true })
    }

    private fun sendResultAndCleanUp(grandResults: Map<String, Boolean>) {
        callback(grandResults.all { it.value })
        detailedCallback(grandResults.mapKeys { Permission.from(it.key) })
        cleanUp()
    }

    private fun cleanUp() {
        requiredPermissions.clear()
        rationale = null
        callback = { }
        detailedCallback = { }
    }

    private fun requestPermissions() {
        permissionCheck.launch(getPermissionList())
    }

    private fun areAllPermissionsGranted(fragment: Fragment)
            = requiredPermissions. all { it.isGranted(fragment) }

    private fun shouldShowPermissionRationale(fragment: Fragment)
            = requiredPermissions.any { it.requiresRationale(fragment) }

    private fun getPermissionList()
            = requiredPermissions.flatMap { it.permissions.toList() }.toTypedArray()

    private fun Permission.isGranted(fragment: Fragment)
            = permissions.all { hasPermission(fragment, it) }

    private fun Permission.requiresRationale(fragment: Fragment)
            = permissions.any { fragment.shouldShowRequestPermissionRationale(it) }

    private fun hasPermission(fragment: Fragment, permission: String) =
        ContextCompat.checkSelfPermission(fragment.requireContext(), permission) == PackageManager.PERMISSION_GRANTED
}