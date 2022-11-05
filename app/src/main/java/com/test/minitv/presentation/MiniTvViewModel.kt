package com.test.minitv.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.minitv.domain.MiniTvRepository
import com.test.minitv.domain.model.MiniTvVideo
import com.test.minitv.domain.model.toReport
import kotlinx.coroutines.launch

class MiniTvViewModel(private val miniTvRepository: MiniTvRepository) : ViewModel() {

    private var _videoSource = MutableLiveData<String>()
    val videoSource: LiveData<String> = _videoSource

    init {
        getNext()
    }

    fun getNext() {
        val currentVideo = miniTvRepository.getNext()
        _videoSource.value = "videos/" + currentVideo.videoIdentifier
        addToReports(currentVideo)
    }

    private fun addToReports(currentVideo: MiniTvVideo) = viewModelScope.launch {
        miniTvRepository.addToReports(currentVideo.toReport())
    }

}