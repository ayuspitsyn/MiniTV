package com.test.minitv.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "reports")
data class Report (
    @PrimaryKey (autoGenerate = true) val id: Int,
    @ColumnInfo val id_video: Int,
    @ColumnInfo val video_name: String,
    @ColumnInfo val startTime: Long
)