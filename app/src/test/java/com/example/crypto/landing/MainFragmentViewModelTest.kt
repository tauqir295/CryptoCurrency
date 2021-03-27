package com.example.crypto.landing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.crypto.TestCoroutineRule
import com.example.network.ApiInterface
import com.example.network.NetworkStatusHelper
import com.example.network.datasource.RemoteDataSource
import com.example.network.interceptor.MockInterceptor
import com.example.network.repository.NetworkRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Test cases for view model.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainFragmentViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var mainFragmentViewModel: MainFragmentViewModel

    @Mock
    private lateinit var networkHelper: NetworkStatusHelper

    @Before
    fun setup() {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(MockInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())

            .baseUrl("https://example.com") //base url for api calling
            .client(okHttpClient)
            .build()

        mainFragmentViewModel =
            MainFragmentViewModel(NetworkRepository(RemoteDataSource(retrofit.create(ApiInterface::class.java))), networkHelper)

    }

    @Test
    fun testList() {
        mainFragmentViewModel.run {
            fetchDataFromAPI()
            Thread.sleep(2000)
            Assert.assertNotNull(currencies.value)

        }
    }

    @Test
    fun testFailedList() {
        mainFragmentViewModel.run {
            fetchDataFromAPI()
            Thread.sleep(2000)
            clearViewModelData()

            Assert.assertNull(currencies.value)

        }
    }

}