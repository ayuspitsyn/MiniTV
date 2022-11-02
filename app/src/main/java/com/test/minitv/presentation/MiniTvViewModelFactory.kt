package com.test.minitv.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MiniTvViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }
}