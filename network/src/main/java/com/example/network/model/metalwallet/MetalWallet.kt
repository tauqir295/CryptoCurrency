package com.example.network.model.metalwallet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetalWallet(
    val dummyMetalWalletList: List<DummyMetalWallet>
) : Parcelable