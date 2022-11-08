package com.test.minitv.data

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.minitv.data.db.MiniTvDao
import com.test.minitv.data.model.toReport
import com.test.minitv.domain.MiniTvRepository
import com.test.minitv.domain.model.MiniTvVideo
import java.io.IOException

class MiniTvRepositoryImpl(
    private val miniTvDao: MiniTvDao,
    private val assets: AssetManager
) : MiniTvRepository {

    override suspend fun getNext(current: MiniTvVideo?): MiniTvVideo {
        val mediaListJson: String? = getJsonFromAssets(MEDIA_LIST_PATH)
        val listType = object : TypeToken<List<MiniTvVideo>>() {}.type
        val medialist: List<MiniTvVideo> = Gson().fromJson(mediaListJson, listType)
        return if (current == null || current == medialist.last() || !medialist.contains(current)) {
            medialist.first()
        } else {
            medialist[medialist.indexOf(current) + 1]
        }
    }

    override suspend fun addToReports(current: MiniTvVideo) {
        miniTvDao.insert(current.toReport())
    }

    private fun getJsonFromAssets(jsonFileName: String): String? {
        val result: String
        try {
            result = assets.open(jsonFileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return result
    }

    companion object {
        const val MEDIA_LIST_PATH = "medialist.json"
    }
}

