package com.test.minitv

import android.app.Application
import com.test.minitv.data.MiniTvDb

class MiniTvApp:Application (){
    val database: MiniTvDb by lazy { MiniTvDb.getDatabase(this)}
}