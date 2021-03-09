package com.example.network.repository

import com.example.network.datasource.AppDataSource
import com.example.network.model.cryptocoins.Cryptocoins
import com.example.network.model.cryptowallet.CryptoWallet
import com.example.network.model.eurwallet.FiatWallets
import com.example.network.model.fiats.Fiats
import com.example.network.model.metals.Metals
import com.example.network.model.metalwallet.MetalWallet
import io.reactivex.rxjava3.core.Observable

class NetworkRepository(private val appDataSource: AppDataSource) {
    fun getCryptoCoins(): Observable<Cryptocoins?>? = appDataSource.getCryptoCoins()

    fun getMetalWallet(): Observable<MetalWallet?>? = appDataSource.getMetalWallet()

    fun getCryptoWallet(): Observable<CryptoWallet?>? = appDataSource.getCryptoWallet()

    fun getFiatWallet(): Observable<FiatWallets?>? = appDataSource.getFiatWallet()

    fun getMetals(): Observable<Metals?>? = appDataSource.getMetals()

    fun getFiats(): Observable<Fiats?>? = appDataSource.getFiats()
}