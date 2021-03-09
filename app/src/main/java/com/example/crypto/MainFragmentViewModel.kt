package com.example.crypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crypto.model.Currency
import com.example.network.NetworkStatusHelper
import com.example.network.Resource
import com.example.network.model.cryptocoins.Cryptocoin
import com.example.network.model.cryptocoins.Cryptocoins
import com.example.network.model.cryptowallet.CryptoWallet
import com.example.network.model.eurwallet.FiatWallets
import com.example.network.model.fiats.Fiat
import com.example.network.model.fiats.Fiats
import com.example.network.model.metals.Metal
import com.example.network.model.metals.Metals
import com.example.network.model.metalwallet.MetalWallet
import com.example.network.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * View model class used for updating the UI
 */
@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val repository: NetworkRepository,
    private val networkHelper: NetworkStatusHelper
) : ViewModel() {

    private val _currencyList = MutableLiveData<Resource<ArrayList<Currency>>>()
    val currencies: LiveData<Resource<ArrayList<Currency>>>
        get() = _currencyList

    init {
        fetchDataFromAPI()
    }

    fun fetchDataFromAPI() {
        _currencyList.postValue(Resource.loading(null))
        if (networkHelper.isNetworkConnected()) {

            Observable.zip(
                repository.getCryptoCoins(),
                repository.getCryptoWallet(),
                repository.getMetalWallet(),
                repository.getMetals(),
                repository.getFiatWallet(),
                repository.getFiats(),
                { cryptocoins: Cryptocoins?,
                  cryptoWallet: CryptoWallet?,
                  metalWallet: MetalWallet?,
                  metal: Metals?,
                  fiatWallets: FiatWallets?,
                  fiats: Fiats? ->

                    filterCurrencies(
                        cryptocoins,
                        cryptoWallet,
                        metalWallet,
                        metal,
                        fiatWallets,
                        fiats
                    )

                }
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn {
                    _currencyList.postValue(Resource.error("No internet connection", null))
                }
                .subscribe()
        }

    }

    private fun filterCurrencies(
        cryptocoins: Cryptocoins?,
        cryptoWallet: CryptoWallet?,
        metalWallet: MetalWallet?,
        metals: Metals?,
        fiatWallets: FiatWallets?,
        fiats: Fiats?
    ) {
        val currencyList = ArrayList<Currency>()
        cryptocoins?.cryptocoins?.forEachIndexed { _, cryptocoin ->
            cryptocoin.run {
                currencyList.add(
                    Currency(
                        name = name,
                        balance = getCryptoWalletBalance(cryptocoin, cryptoWallet),
                        icon = logo,
                        symbol = symbol,
                        type = "CryptoCoin",
                        price = price
                    )
                )
            }
        }

        metals?.metals?.forEachIndexed { _, metal ->
            metal.run {
                currencyList.add(
                    Currency(
                        name = name,
                        balance = getMetalWalletBalance(metal, metalWallet),
                        icon = logo,
                        symbol = symbol,
                        type = "Metal",
                        price = price
                    )
                )
            }
        }

        fiats?.fiats?.forEachIndexed { _, fiat ->
            fiat.run {
                currencyList.add(
                    Currency(
                        name = name,
                        balance = getFiatWalletBalance(fiat, fiatWallets),
                        icon = logo,
                        symbol = symbol,
                        type = "Fiat",
                        price = 0.0
                    )
                )
            }
        }

        _currencyList.postValue(Resource.success(currencyList))
    }

    private fun getCryptoWalletBalance(
        cryptocoin: Cryptocoin,
        cryptoWallet: CryptoWallet?
    ): Double {
        cryptoWallet?.dummyCryptoWalletList?.forEach { dummyCryptoWallet ->
            if (cryptocoin.id == dummyCryptoWallet.id)
                return dummyCryptoWallet.balance
        }

        return 0.0
    }

    private fun getMetalWalletBalance(
        metal: Metal,
        metalWallet: MetalWallet?
    ): Double {
        metalWallet?.dummyMetalWalletList?.forEach { dummyMetalWallet ->
            if (metal.id == dummyMetalWallet.id)
                return dummyMetalWallet.balance
        }

        return 0.0
    }

    private fun getFiatWalletBalance(
        fiat: Fiat,
        fiatWallets: FiatWallets?
    ): Double {
        fiatWallets?.dummyFiatWallet?.forEach { wallet ->
            if (fiat.id == wallet.id)
                return wallet.balance
        }

        return 0.0
    }

}