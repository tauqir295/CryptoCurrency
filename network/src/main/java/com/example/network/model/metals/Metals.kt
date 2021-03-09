package com.example.network.model.metals

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Metals(
    val metals: List<Metal>
) : Parcelable