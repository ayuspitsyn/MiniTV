package com.test.minitv.presentation

import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.test.minitv.MiniTvApp
import com.test.minitv.R
import com.test.minitv.presentation.vm.MiniTvViewModel
import com.test.minitv.presentation.vm.MiniTvViewModelFactory

class MainActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private val miniTvViewModel: MiniTvViewModel by viewModels {
        MiniTvViewModelFactory(
            (application as MiniTvApp).database.miniTvDao(),
            assets
        )
    }

    private lateinit var surfaceView: SurfaceView
    private lateinit var player: MediaPlayer
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var videoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        miniTvViewModel.current.observe(this@MainActivity) {
            videoPath = VIDEOS_FOLDER + it?.videoIdentifier
        }

        setContentView(R.layout.activity_main)
        surfaceView = findViewById(R.id.surfaceView)
        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this@MainActivity)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        player = MediaPlayer()
        player.setDisplay(surfaceHolder)
        try {
            setupPlayer()
        } catch (e: java.lang.Exception) {
            Log.e(LOG_TAG, e.toString())
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(p0: SurfaceHolder) {}

    private fun setupPlayer() {
        val afd: AssetFileDescriptor = assets.openFd(videoPath)
        player.apply {
            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            prepare()
            setOnPreparedListener { start() }
            setOnCompletionListener { onCompletionCallback(it) }
            setAudioAttributes(
                AudioAttributes
                    .Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
        }
    }

    private fun onCompletionCallback(it: MediaPlayer?) {
        miniTvViewModel.getNext()
        if (it == null) {
            player = MediaPlayer()
            player.setDisplay(surfaceHolder)
        } else {
            player.reset()
        }
        try {
            setupPlayer()
        } catch (e: java.lang.Exception) {
            Log.e(LOG_TAG, e.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        if (::player.isInitialized) {
            player.release()
        }
    }

    companion object {
        const val VIDEOS_FOLDER = "videos/"
        const val LOG_TAG = "LOG_TAG"
    }
}