package com.example.crypto.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crypto.model.Currency
import com.example.network.NetworkStatusHelper
import com.example.network.Resource
import com.example.network.model.cryptocoins.Cryptocoins
import com.example.network.model.cryptowallet.CryptoWallet
import com.example.network.model.eurwallet.FiatWallets
import com.example.network.model.fiats.Fiats
import com.example.network.model.metals.Metals
import com.example.network.model.metalwallet.MetalWallet
import com.example.network.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random.Default.nextInt

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

    private var currencyList = ArrayList<Currency>()
    private var filteredCurrencyList = ArrayList<Currency>()

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
                    { cryptoCoins: Cryptocoins?,
                      cryptoWallet: CryptoWallet?,
                      metalWallet: MetalWallet?,
                      metal: Metals?,
                      fiatWallets: FiatWallets?,
                      fiats: Fiats? ->

                        filterCurrencies(
                                cryptoCoins,
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
            cryptoCoins: Cryptocoins?,
            cryptoWallet: CryptoWallet?,
            metalWallet: MetalWallet?,
            metals: Metals?,
            fiatWallets: FiatWallets?,
            fiats: Fiats?
    ) {
        currencyList.clear()

        cryptoCoins?.cryptocoins?.forEach { cryptoCoin ->
            cryptoCoin.run {
                cryptoWallet?.dummyCryptoWalletList?.forEach { wallet ->
                    if (cryptoCoin.id == wallet.cryptocoinId && !wallet.deleted) {
                        currencyList.add(
                                Currency(
                                        name = name,
                                        balance = wallet.balance,
                                        icon = logo,
                                        symbol = symbol,
                                        type = "CryptoCoin",
                                        price = price,
                                        isCryptoCurrency = true
                                )
                        )
                    }
                }

            }
        }

        metals?.metals?.forEach { metal ->
            metal.run {
                metalWallet?.dummyMetalWalletList?.forEach { wallet ->
                    if (metal.id == wallet.metalId && !wallet.deleted) {
                        currencyList.add(
                                Currency(
                                        name = name,
                                        balance = wallet.balance,
                                        icon = logo,
                                        symbol = symbol,
                                        type = "Metal",
                                        price = price,
                                        isMetal = true
                                )
                        )
                    }
                }
            }
        }

        fiats?.fiats?.forEach { fiat ->
            fiat.run {
                fiatWallets?.dummyFiatWallet?.forEach { wallet ->
                    if (fiat.id == wallet.fiatId && !wallet.deleted) {
                        currencyList.add(
                                Currency(
                                        name = name,
                                        balance = wallet.balance,
                                        icon = logo,
                                        symbol = symbol,
                                        type = "Fiat",
                                        price = 0.0,
                                        isFiat = true
                                )
                        )
                    }
                }
            }
        }

        updateCurrencies()
    }

    /**
     * sort currencies based on type i.e. metals, cryptoCurrencies, fiats and balance.
     * in order to keep fiat first, the sorting parameter [isFiat] is kept at 3rd position in compareBy operators
     * to keep any parameter on top, set boolean parameter from 3 to 1 (i.e. in reverse order) inside compareBy operators
     * and then balance follows 4th position of operator used.
     */
    fun sortCurrencies() {

        val sortedCurrencies = filteredCurrencyList.sortedWith(compareBy({ it.isMetal }, { it.isCryptoCurrency }, {it.isFiat}, {it.balance}))

        _currencyList.postValue(Resource.success(sortedCurrencies.toList() as ArrayList<Currency>))
    }

    /**
     * Filter currencies based on type
     */
    fun filterCurrencies() {
        val filterList = arrayOf("Fiat", "CryptoCoin", "Metal")
        val randomPosition = nextInt(filterList.size)

        filteredCurrencyList = currencyList.filter {
            it.type in setOf(filterList[randomPosition])
        } as ArrayList<Currency>

        _currencyList.postValue(Resource.success(filteredCurrencyList))
    }

    /**
     * update the currency to api version
     */
    fun updateCurrencies() {
        filteredCurrencyList = currencyList
        _currencyList.postValue(Resource.success(filteredCurrencyList))
    }

    /**
     * clear data in view model
     */
    fun clearViewModelData() {
        _currencyList.value = null
    }
}