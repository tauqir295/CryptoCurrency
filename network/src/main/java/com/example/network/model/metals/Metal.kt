package com.example.network.model.metals

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Metal(
    val id: String,
    val logo: String,
    val name: String,
    val price: Double,
    val symbol: String
) : Parcelable