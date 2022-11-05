package com.test.minitv.presentation

import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.test.minitv.R

class MainActivity : AppCompatActivity()
    ,  SurfaceHolder.Callback
    , MediaPlayer.OnPreparedListener
{

    private lateinit var surfaceView:SurfaceView // = SurfaceView(this)
    private lateinit var player: MediaPlayer
    private lateinit var surfaceHolder: SurfaceHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        surfaceView = findViewById<SurfaceView>(R.id.surfaceView)
        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this@MainActivity)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        player = MediaPlayer()
        player.setDisplay(surfaceHolder)
        try {
            val afd:AssetFileDescriptor = assets.openFd("videos/video8.mp4")
            player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            player.prepare()
            player.setOnPreparedListener(this@MainActivity)
            player.setAudioAttributes(AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());
        } catch(e: java.lang.Exception) {
            Log.e("LOG_TAG", "Error="+e)
        }
    }

    override fun onPause() {
        super.onPause()
        stopPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlayer()
    }

    private fun stopPlayer() {
        if(::player.isInitialized) {
            player.release()
        }
    }
    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(p0: SurfaceHolder) {}

    override fun onPrepared(p0: MediaPlayer?) {
        player.start()
    }
}