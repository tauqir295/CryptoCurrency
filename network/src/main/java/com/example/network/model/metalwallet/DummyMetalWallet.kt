package com.example.network.model.metalwallet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyMetalWallet(
    val balance: Double,
    val deleted: Boolean,
    val id: String,
    val isDefault: Boolean,
    val metalId: String,
    val name: String
) : Parcelable