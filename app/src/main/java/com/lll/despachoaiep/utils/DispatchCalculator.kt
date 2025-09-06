// java/com/lll/despachoaiep/utils/DispatchCalculator.kt
package com.lll.despachoaiep.utils


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