package com.ain.trading.customviews

import android.app.Application
import com.ain.trading.api.AinUserApiService
import com.ain.trading.api.AinUserNetworkService

class MyApplication : Application() {
    companion object {

        var instance: MyApplication? = MyApplication()
        var networkService:AinUserApiService ? = null
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
          networkService = AinUserNetworkService().getApi()

    }
}