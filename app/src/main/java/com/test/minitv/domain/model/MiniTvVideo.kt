package com.test.minitv.domain.model

import com.test.minitv.data.Report
import java.util.*

data class MiniTvVideo(
    val videoId: Int,
    val videoIdentifier: String,
    val orderNumber: Int
)

fun MiniTvVideo.toReport(): Report {
    return Report(
        0,
        videoId,
        videoIdentifier,
        Calendar.getInstance().timeInMillis
    )
}