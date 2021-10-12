package me.omico.xero.core

import android.app.Application

abstract class CoreApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        lateinit var instance: Application
            private set
    }
}

val applicationContext: Application = CoreApplication.instance
