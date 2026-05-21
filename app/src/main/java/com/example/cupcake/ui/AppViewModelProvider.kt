package com.example.a211021_aininnajihah_mrnelson_lab05.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a211021_aininnajihah_mrnelson_lab05.CupcakeApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            OrderViewModel(
                cupcakeApplication().container.ordersRepository
            )
        }
    }
}

fun CreationExtras.cupcakeApplication(): CupcakeApplication =
    (this[APPLICATION_KEY] as CupcakeApplication)