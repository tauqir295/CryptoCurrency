package com.example.network.model.cryptocoins

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cryptocoins(
    val cryptocoins: List<Cryptocoin>
): Parcelable