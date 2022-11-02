package com.test.minitv.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface MiniTvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: Reports)

}