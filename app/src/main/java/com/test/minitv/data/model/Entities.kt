package com.test.minitv.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.minitv.domain.model.MiniTvVideo
import java.util.*

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "id_video") val id_video: Int,
    @ColumnInfo(name = "video_name") val video_name: String,
    @ColumnInfo(name = "startTime") val startTime: Long
)

fun MiniTvVideo.toReport(): Report {
    return Report(
        id = 0,
        id_video = videoId,
        video_name = videoIdentifier,
        startTime = Calendar.getInstance().timeInMillis
    )
}