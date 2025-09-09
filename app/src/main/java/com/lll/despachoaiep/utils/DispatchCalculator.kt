// java/com/lll/despachoaiep/utils/DispatchCalculator.kt
package com.lll.despachoaiep.utils

import android.util.Log
import kotlin.math.*


fun calcularDespacho(montoCompra: Int, distanciaKm: Double): Int {
    return when {
        montoCompra >= 50000 && distanciaKm <= 20 -> 0
        montoCompra in 25000..49999 -> (150 * distanciaKm).toInt()
        else -> (300 * distanciaKm).toInt()
    }
}

fun estaDentroDelRadio(distanciaKm: Double): Boolean = distanciaKm <= 20.0


fun validarYCalcularDespacho(
    montoStr: String,
    distanciaStr: String,
    onError: (String) -> Unit,
    onSuccess: (Int) -> Unit
) {
    val monto = montoStr.toIntOrNull()
    val distancia = distanciaStr.toDoubleOrNull()

    if (monto == null || distancia == null) {
        onError("Por favor, rellene todos los campos obligatorios.")
        return
    }
    if (!estaDentroDelRadio(distancia)) {
        onError("Distancia fuera del radio permitido (20 km).")
        return
    }


    val costo = calcularDespacho(monto, distancia)
    onSuccess(costo)

}


fun convertirAGrados(radianes: Double): Double {
    val grados = Math.toDegrees(radianes)
    Log.d("DespachoAIEP", "Radianes: $radianes → Grados: $grados")
    return grados
}


fun calcularDistanciaHaversine(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double
): Double {
    val R = 6371.0 // Radio de la Tierra en km
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val a = sin(dLat / 2).pow(2) +
            cos(Math.toRadians(lat1)) *
            cos(Math.toRadians(lat2)) *
            sin(dLon / 2).pow(2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c
}
