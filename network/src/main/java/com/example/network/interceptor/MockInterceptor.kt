package com.example.network.interceptor

import com.example.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * This will help us to test our networking code if a particular API is fails.
 */
class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.endsWith("dummyMetalWalletList") -> readDataFromLocal("dummyMetalWallets")
                uri.endsWith("dummyCryptoWalletList") -> readDataFromLocal("dummyCryptoWallets")
                uri.endsWith("dummyFiatWallets") -> readDataFromLocal("dummyFiatWallets")
                uri.endsWith("cryptocoins") -> readDataFromLocal("cryptocoins")
                uri.endsWith("fiats") -> readDataFromLocal("fiats")
                uri.endsWith("metals") -> readDataFromLocal("metals")
                else -> ""
            }

            return Response.Builder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray().toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .request(chain.request())
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError("MockInterceptor is only meant for Testing Purposes and " +
                    "bound to be used only with DEBUG mode")
        }
    }

    private fun readDataFromLocal(fileName: String): String {
        val classLoader = Thread.currentThread().contextClassLoader
        classLoader?.getResourceAsStream("$fileName.json").use {
            return it?.reader()?.readText()?:""
        }
    }

}