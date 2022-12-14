package com.example.cleanexample

import android.app.Application
import com.example.cleanexample.ui.di.AppContainer

class MyApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
    // Instance of AppContainer that will be used by all the Activities of the app
    //val appContainer = AppContainer(applicationContext)
}