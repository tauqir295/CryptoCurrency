package com.example.network.model.cryptocoins

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cryptocoin(
    val id: String,
    val logo: String,
    val name: String,
    val price: Double,
    val symbol: String
): Parcelable