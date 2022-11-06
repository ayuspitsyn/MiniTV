package com.test.minitv.presentation.vm

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.minitv.data.MiniTvRepositoryImpl
import com.test.minitv.data.db.MiniTvDao

class MiniTvViewModelFactory(miniTvDao: MiniTvDao, assets: AssetManager) :
    ViewModelProvider.Factory {

    private val reportsRepository by lazy {
        MiniTvRepositoryImpl(miniTvDao, assets)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MiniTvViewModel(reportsRepository) as T
    }
}