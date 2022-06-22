package com.ain.trading.api

import com.ain.trading.utils.AinPref
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class AinUserNetworkService {
    companion object {
       /* val BASE_URL =
            "http://aintradinglaravel.kawikasolutions.com/api/" //new url for client testing*/

        val BASE_URL =
            "http://alokartlaravel.kawikasolutions.com/api/" //new url for client testing
    }

    var networkApi: AinUserApiService? = getApi()

    fun getApi(): AinUserApiService? {

        var logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()

        httpClient.readTimeout(0, TimeUnit.SECONDS).connectTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS)

        httpClient.addInterceptor(HeaderInterceptor())

        httpClient.addInterceptor(logging)


        var retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().serializeNulls().create()
            )
        )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build()).build()


        return retrofit.create(AinUserApiService::class.java)
    }

    private class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request().newBuilder()
               // .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", AinPref.getAuthToken())
                .addHeader("language",AinPref.getLanguageId())
                .build()


            return chain.proceed(request)
        }
    }
}