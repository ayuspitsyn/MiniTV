package com.test.minitv.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.test.minitv.data.model.Report

@Dao
interface MiniTvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: Report)

}