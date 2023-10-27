package com.joaoflaviofreitas.inchurch.data.remote.service.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.joaoflaviofreitas.inchurch.utils.Constants.API_KEY
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

class ConnectivityInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isAvailableNetwork) {
            throw IOException("No Internet")
        }
        val original = chain.request()

        val url = original.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
    private val isAvailableNetwork: Boolean get() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }
}
