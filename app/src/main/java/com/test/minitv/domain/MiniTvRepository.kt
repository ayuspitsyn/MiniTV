package com.test.minitv.domain

import com.test.minitv.domain.model.MiniTvVideo

interface MiniTvRepository {

    suspend fun prepare()

    fun getNext(current: MiniTvVideo?): MiniTvVideo

    suspend fun addToReports(current: MiniTvVideo)

}