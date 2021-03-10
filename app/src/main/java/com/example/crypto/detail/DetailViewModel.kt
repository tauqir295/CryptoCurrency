package com.example.crypto.detail

import androidx.lifecycle.ViewModel
import com.example.crypto.model.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * View model class used for updating the UI
 */
@HiltViewModel
class DetailViewModel @Inject constructor(): ViewModel() {
    var currency: Currency? = null
}