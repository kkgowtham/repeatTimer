package com.gowtham.repeattimer

import android.app.Application
import androidx.lifecycle.ViewModelProvider

class MyApplication : Application() {

    private lateinit var appViewModel: AppViewModel

    override fun onCreate() {
        super.onCreate()
        app = this
        appViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this).create(AppViewModel::class.java)
    }

    companion object {
        var app: MyApplication? = null

        fun getInstance(): MyApplication? {
            return app
        }

        fun getAppViewModel(): AppViewModel? {
            return getInstance()?.appViewModel
        }
    }
}