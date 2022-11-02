package com.test.minitv.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Reports::class], version = 1)
abstract class MiniTvDb: RoomDatabase() {

    abstract fun miniTvDao(): MiniTvDao

    companion object {
        private lateinit var INSTANCE: MiniTvDb
        fun getDatabase(context: Context): MiniTvDb {
            if (!::INSTANCE.isInitialized) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MiniTvDb::class.java,
                        "minitv"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}