package com.example.a211021_aininnajihah_mrnelson_lab05

import android.app.Application
import com.example.a211021_aininnajihah_mrnelson_lab05.data.AppContainer
import com.example.a211021_aininnajihah_mrnelson_lab05.data.AppDataContainer

class CupcakeApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}