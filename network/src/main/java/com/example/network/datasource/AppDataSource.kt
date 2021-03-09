package com.example.network.datasource

import com.example.network.model.cryptocoins.Cryptocoins
import com.example.network.model.cryptowallet.CryptoWallet
import com.example.network.model.eurwallet.FiatWallets
import com.example.network.model.fiats.Fiats
import com.example.network.model.metals.Metals
import com.example.network.model.metalwallet.MetalWallet
import io.reactivex.rxjava3.core.Observable

/**
 * Interface for API class
 */
interface AppDataSource {
    fun getCryptoCoins(): Observable<Cryptocoins?>?

    fun getMetalWallet(): Observable<MetalWallet?>?

    fun getCryptoWallet(): Observable<CryptoWallet?>?

    fun getFiatWallet(): Observable<FiatWallets?>?

    fun getMetals(): Observable<Metals?>?

    fun getFiats(): Observable<Fiats?>?
}