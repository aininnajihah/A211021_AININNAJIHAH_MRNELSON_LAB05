package com.example.a211021_aininnajihah_mrnelson_lab05.data

import android.content.Context

interface AppContainer {
    val ordersRepository: OrdersRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val ordersRepository: OrdersRepository by lazy {
        OfflineOrdersRepository(
            CupcakeDatabase.getDatabase(context).orderDao()
        )
    }
}