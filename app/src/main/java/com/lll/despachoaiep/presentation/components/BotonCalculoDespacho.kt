package com.lll.despachoaiep.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lll.despachoaiep.utils.obtenerTemperaturaCamion
import com.lll.despachoaiep.utils.validarYCalcularDespacho

@Composable
fun BotonCalculoDespacho(
    productosSeleccionados: List<Int>,
    incluyeCongelados: Boolean,
    montoCompra: String,
    distanciaKm: String,
    montoCompraInt: Int,
    direccionDespacho: String,
    contactoDespacho: String,
    onError: (String) -> Unit,
    onSuccess: (Int) -> Unit,
    temperaturaCamion: Double
) {



    Button(
        onClick = {
            if (productosSeleccionados.isEmpty()) {
                onError("Debes seleccionar al menos un producto antes de calcular el despacho.")
                return@Button
            }
            // aqui evaluo si incluye congelados
            if (incluyeCongelados) {
                val temperatura = temperaturaCamion
                Log.d("TEMPERATURA", temperatura.toString())

                // seteo mi rango de temperatura
                val rangoMinimo = -18.0
                val rangoMaximo = -10.0

                val temperaturaValida = temperatura in rangoMinimo..rangoMaximo

                if (!temperaturaValida) {
                    onError("⚠️ Alerta: Temperatura del camión es $temperatura °C. No se puede despachar productos congelados.")
                    return@Button
                }

            }
            // por ahora solo valido que los campos tengan algo
            if (direccionDespacho.isEmpty()) {
                onError("Debes ingresar una dirección de despacho.")
                return@Button
            }
            if (contactoDespacho.isEmpty()) {
                onError("Debes ingresar un contacto de despacho.")
                return@Button
            }
            // envio el montoCompra a la funcion
            validarYCalcularDespacho(
                montoCompra, distanciaKm, onError = onError, onSuccess = onSuccess
            )
        }, modifier = Modifier.fillMaxWidth()
    ) {
        Text("Calcular despacho")
    }
}