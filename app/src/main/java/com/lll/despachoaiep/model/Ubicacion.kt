package com.lll.despachoaiep.model

data class UbicacionGps(
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis(),
    val nombreUsuario: String = "",
    val correo: String = ""

)
