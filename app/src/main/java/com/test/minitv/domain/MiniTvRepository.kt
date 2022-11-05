package com.test.minitv.domain

import com.test.minitv.data.Report
import com.test.minitv.domain.model.MiniTvVideo

interface MiniTvRepository {

    suspend fun addToReports(report: Report)

    fun getNext(): MiniTvVideo
}