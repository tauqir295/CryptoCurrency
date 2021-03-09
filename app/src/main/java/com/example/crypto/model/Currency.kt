package com.example.crypto.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency (
    val icon: String,
    val symbol: String,
    val balance: Double,
    val name: String,
    val type: String,
    val price: Double
) : Parcelable
