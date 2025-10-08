package com.lll.despachoaiep.datos.network

import com.lll.despachoaiep.BuildConfig

object OpenRouteServiceConfig {
    const val BASE_URL = "https://api.openrouteservice.org/"
    val API_KEY: String = BuildConfig.OPENROUTESERVICE_API_KEY
}

