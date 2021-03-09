package com.example.crypto.landing

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.crypto.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity for entry point in cat library.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(
                R.id.container,
                MainFragment.newInstance()
        ).commitNow()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // navigating back when home icon is pressed
            }
        }
        return super.onOptionsItemSelected(item)
    }
}