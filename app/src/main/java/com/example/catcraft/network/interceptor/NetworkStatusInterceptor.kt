package com.example.catcraft.network.interceptor

import com.example.catcraft.network.utils.ConnectionManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkStatusInterceptor @Inject constructor(private val connectionManager: ConnectionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (connectionManager.isConnected()) {
            chain.proceed(chain.request())
        } else {
            throw IOException("No internet available, please check your WiFi or Data connection")
        }
    }
}