package com.test.minitv.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.minitv.data.MiniTvDao
import com.test.minitv.data.Reports
import kotlinx.coroutines.launch
import java.net.URI

class MiniTvViewModel(private val miniTvDao:MiniTvDao): ViewModel() {

    private var _videoSource = MutableLiveData<URI>()
    val videoSource: LiveData<URI> = _videoSource

    init {

    }

    override fun onCleared() {
        super.onCleared()
    }

    fun addToReports(report: Reports) = viewModelScope.launch{
        miniTvDao.insert(report)
    }

}