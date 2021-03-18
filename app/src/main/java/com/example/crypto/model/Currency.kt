package com.example.crypto.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency (
    val icon: String,
    val symbol: String,
    val balance: Double = 0.0,
    val name: String,
    val type: String,
    val isMetal: Boolean = false,
    val isCryptoCurrency: Boolean = false,
    val isFiat: Boolean = false,
    val price: Double,
    val wallets: ArrayList<CurrencyWallet>? = null
) : Parcelable

@Parcelize
data class CurrencyWallet (
    val balance: Double,
    val deleted: Boolean,
    val id: String,
    val isDefault: Boolean,
    val name: String
): Parcelable
