package com.example.network.model.eurwallet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FiatWallets(
    val dummyFiatWallet: List<FiatWallet>
) : Parcelable