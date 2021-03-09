package com.example.network.model.cryptowallet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoWallet(
    val dummyCryptoWalletList: List<DummyCryptoWallet>
): Parcelable