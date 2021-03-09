package com.example.network.model.fiats

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fiat(
    val id: String,
    val logo: String,
    val name: String,
    val symbol: String
) : Parcelable