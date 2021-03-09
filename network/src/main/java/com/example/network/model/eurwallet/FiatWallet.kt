package com.example.network.model.eurwallet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FiatWallet(
    val balance: Double,
    val deleted: Boolean,
    val fiatId: String,
    val id: String,
    val isDefault: Boolean,
    val name: String
) : Parcelable