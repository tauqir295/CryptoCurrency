package com.example.crypto

import android.util.Log
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
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * View model class used for updating the UI
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NetworkRepository,
    private val networkHelper: NetworkStatusHelper
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val listOfCurrencies = emptyList<Currency>()

    private val _cryptoCoins = MutableLiveData<Resource<Cryptocoins>>()
    val cryptoCoins: LiveData<Resource<Cryptocoins>>
        get() = _cryptoCoins

    private val _cryptoWallet = MutableLiveData<Resource<CryptoWallet>>()
    val cryptoWallet: LiveData<Resource<CryptoWallet>>
        get() = _cryptoWallet

    private val _metalWallets = MutableLiveData<Resource<MetalWallet>>()
    val metalWallets: LiveData<Resource<MetalWallet>>
        get() = _metalWallets

    private val _metals = MutableLiveData<Resource<Metals>>()
    val metals: LiveData<Resource<Metals>>
        get() = _metals

    private val _fiatWallets = MutableLiveData<Resource<FiatWallets>>()
    val fiatWallet: LiveData<Resource<FiatWallets>>
        get() = _fiatWallets

    private val _fiats = MutableLiveData<Resource<Fiats>>()
    val fiat: LiveData<Resource<Fiats>>
        get() = _fiats

    init {
        getCryptoWallet()
        getCryptoCoins()
        getMetalWallets()
        getMetals()
        getFiatWallet()
        getFiats()
    }

    private fun getCryptoCoins() {
        if (networkHelper.isNetworkConnected()) {
            disposables.add(repository.getCryptoCoins()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result ->

                    result?.let {
                        _cryptoCoins.postValue(Resource.success(it))
                    }


                }) { _ ->
                    _cryptoCoins.postValue(Resource.error("No internet connection", null))
                }
            )
        } else {
            _cryptoCoins.postValue(Resource.error("No internet connection", null))
        }

    }

    private fun getCryptoWallet() {
        if (networkHelper.isNetworkConnected()) {
            disposables.add(repository.getCryptoWallet()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result ->

                    result?.let {
                        _cryptoWallet.postValue(Resource.success(it))
                    }


                }) { _ ->
                    _cryptoWallet.postValue(Resource.error("No internet connection", null))
                }
            )
        } else {
            _cryptoWallet.postValue(Resource.error("No internet connection", null))
        }

    }

    private fun getMetalWallets() {
        if (networkHelper.isNetworkConnected()) {
            disposables.add(repository.getMetalWallet()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result ->

                    result?.let {
                        _metalWallets.postValue(Resource.success(it))
                    }


                }) { _ ->
                    _metalWallets.postValue(Resource.error("No internet connection", null))
                }
            )
        } else {
            _metalWallets.postValue(Resource.error("No internet connection", null))
        }

    }

    private fun getMetals() {
        if (networkHelper.isNetworkConnected()) {
            disposables.add(repository.getMetals()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result ->

                    result?.let {
                        _metals.postValue(Resource.success(it))
                    }


                }) { _ ->
                    _metals.postValue(Resource.error("No internet connection", null))
                }
            )
        } else {
            _metals.postValue(Resource.error("No internet connection", null))
        }

    }

    private fun getFiatWallet() {
        if (networkHelper.isNetworkConnected()) {
            disposables.add(repository.getFiatWallet()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result ->

                    result?.let {
                        _fiatWallets.postValue(Resource.success(it))
                    }


                }) { _ ->
                    _fiatWallets.postValue(Resource.error("No internet connection", null))
                }
            )
        } else {
            _fiatWallets.postValue(Resource.error("No internet connection", null))
        }

    }

    private fun getFiats() {
        if (networkHelper.isNetworkConnected()) {
            disposables.add(repository.getFiats()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result ->

                    result?.let {
                        _fiats.postValue(Resource.success(it))
                    }


                }) { _ ->
                    _fiats.postValue(Resource.error("No internet connection", null))
                }
            )
        } else {
            _fiats.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fetchDataFromAPI() {
        val a1 = repository.getCryptoCoins()

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
              fiats: Fiats?->

                Log.d("", "")
            }
        )
            .onErrorReturn { error ->

                Log.d("", error.message.toString())

            }

    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}