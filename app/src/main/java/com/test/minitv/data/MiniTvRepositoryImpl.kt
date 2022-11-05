package com.test.minitv.data

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.minitv.data.db.MiniTvDao
import com.test.minitv.domain.MiniTvRepository
import com.test.minitv.domain.model.MiniTvVideo
import java.io.IOException

private const val MEDIA_LIST_PATH = "medialist.json"

class MiniTvRepositoryImpl(
    private val miniTvDao: MiniTvDao,
    private val assets: AssetManager
) : MiniTvRepository {

    private var medialist: List<MiniTvVideo>
    private var currentVideo: MiniTvVideo

    init {
        val medialistJson: String? = getJsonFromAssets(MEDIA_LIST_PATH)
        val listType = object : TypeToken<List<MiniTvVideo>>() {}.type
        medialist = Gson().fromJson(medialistJson, listType)
        currentVideo = medialist.first()
    }

    override suspend fun addToReports(report: Report) {
        miniTvDao.insert(report)
    }

    override fun getNext(): MiniTvVideo {
        val result = currentVideo
        if (currentVideo == medialist.last()) {
            currentVideo = medialist.first()
        } else {
            currentVideo = medialist[medialist.indexOf(currentVideo) + 1]
        }
        return result
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

}