package com.example.network.datasource

import com.example.network.ApiInterface
import com.example.network.model.cryptocoins.Cryptocoins
import com.example.network.model.cryptowallet.CryptoWallet
import com.example.network.model.eurwallet.FiatWallets
import com.example.network.model.fiats.Fiats
import com.example.network.model.metals.Metals
import com.example.network.model.metalwallet.MetalWallet
import io.reactivex.rxjava3.core.Observable

/**
 * Interface implementation for AppDataSource
 * @param - [ApiInterface] - class is used for fetching data from server
 */
class RemoteDataSource(private val apiInterface: ApiInterface) : AppDataSource {
    override fun getCryptoCoins(): Observable<Cryptocoins?>? = apiInterface.getCryptoCoins()

    override fun getMetalWallet(): Observable<MetalWallet?>? = apiInterface.getMetalWallet()

    override fun getCryptoWallet(): Observable<CryptoWallet?>? = apiInterface.getCryptoWallet()

    override fun getFiatWallet(): Observable<FiatWallets?>? = apiInterface.getFiatWallet()

    override fun getMetals(): Observable<Metals?>? = apiInterface.getMetals()

    override fun getFiats(): Observable<Fiats?>? = apiInterface.getFiats()

}