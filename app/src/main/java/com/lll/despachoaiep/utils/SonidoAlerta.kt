package com.lll.despachoaiep.utils

import android.content.Context
import android.media.MediaPlayer
import com.lll.despachoaiep.R

fun reproducirSonidoError(context: Context) {
    val mediaPlayer = MediaPlayer.create(context, R.raw.alerta_error)
    mediaPlayer?.start()
}