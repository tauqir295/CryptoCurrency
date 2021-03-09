package com.example.crypto.util

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.crypto.R

/**
 * using extension function to extend a class with new functionality.
 * Basically, an extension function is a member function of a class
 * that is defined outside the class.
 *
 * extension method for navigating to other fragment
 *
 */
fun replaceWithNextFragment(
    containerID: Int,
    fragmentManager: FragmentManager?,
    fragment: Fragment,
    arguments: Bundle?,
    addToBackStack: Boolean = true
) {
    arguments.let {
        fragment.arguments = it
    }

    fragmentManager?.let {
        it.beginTransaction().apply {
            replace(containerID, fragment)
            if (addToBackStack) {
                addToBackStack(fragment::class.simpleName)
            }
            commit()
        }
    }
}

/**
 * handing API failed case by showing alert dialog
 */
fun handleAPIFail(activity: AppCompatActivity?) {
    activity?.run {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.api_failed))
            .setMessage(getString(R.string.something_went_wrong))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                onBackPressed()
            }
            .setCancelable(false)
            .show()
    }
}
