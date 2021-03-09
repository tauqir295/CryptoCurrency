package com.example.network

import com.example.network.model.cryptocoins.Cryptocoins
import com.example.network.model.cryptowallet.CryptoWallet
import com.example.network.model.eurwallet.FiatWallets
import com.example.network.model.fiats.Fiats
import com.example.network.model.metals.Metals
import com.example.network.model.metalwallet.MetalWallet
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

/**
 * retrofit service interface for defining the api call methods
 */
interface ApiInterface {

    @GET("/cryptocoins")
    fun getCryptoCoins(): Observable<Cryptocoins?>?

    @GET("/dummyMetalWalletList")
    fun getMetalWallet(): Observable<MetalWallet?>?

    @GET("/dummyCryptoWalletList")
    fun getCryptoWallet(): Observable<CryptoWallet?>?

    @GET("/dummyFiatWallets")
    fun getFiatWallet(): Observable<FiatWallets?>?

    @GET("/metals")
    fun getMetals(): Observable<Metals?>?

    @GET("/fiats")
    fun getFiats(): Observable<Fiats?>?

}