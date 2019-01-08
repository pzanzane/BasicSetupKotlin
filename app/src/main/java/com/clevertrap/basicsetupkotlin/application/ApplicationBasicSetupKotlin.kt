package com.clevertrap.basicsetupkotlin.application

import android.app.Application
import android.content.Context
import com.clevertrap.basicsetupkotlin.utility.DeviceInfoUtil

class ApplicationBasicSetupKotlin: Application() {


    override fun onCreate() {
        super.onCreate()

        instance = this

        DeviceInfoUtil().toastDeviceConfig(this,true)

    }

    companion object {
        private var instance: ApplicationBasicSetupKotlin? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }



}