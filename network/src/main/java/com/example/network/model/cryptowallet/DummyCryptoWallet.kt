package com.example.network.model.cryptowallet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyCryptoWallet(
    val balance: Double,
    val cryptocoinId: String,
    val deleted: Boolean,
    val id: String,
    val isDefault: Boolean,
    val name: String
): Parcelable