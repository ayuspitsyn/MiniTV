package com.test.minitv.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.minitv.domain.MiniTvRepository
import com.test.minitv.domain.model.MiniTvVideo
import kotlinx.coroutines.launch

class MiniTvViewModel(private val miniTvRepository: MiniTvRepository) : ViewModel() {

    private var _current = MutableLiveData<MiniTvVideo?>()
    val current: LiveData<MiniTvVideo?> = _current

    init {
        getNext()
    }

    fun getNext() {
        viewModelScope.launch {
            _current.value = miniTvRepository.getNext(current.value)
            current.value?.let { miniTvRepository.addToReports(it) }
        }
    }
}