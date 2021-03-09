package com.example.network.model.fiats

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fiats(
    val fiats: List<Fiat>
) : Parcelable