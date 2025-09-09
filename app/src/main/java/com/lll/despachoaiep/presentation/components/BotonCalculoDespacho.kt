package com.lll.despachoaiep.presentation.components

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
    onError: (String) -> Unit,
    onSuccess: (Int) -> Unit
) {
    Button(
        onClick = {
            if (productosSeleccionados.isEmpty()) {
                onError("Debes seleccionar al menos un producto antes de calcular el despacho.")
                return@Button
            }

            if (incluyeCongelados) {
                val temperatura = obtenerTemperaturaCamion()
                if (temperatura > -5.0) {
                    onError("⚠️ Alerta: Temperatura del camión es $temperatura °C. No se puede despachar productos congelados.")
                    return@Button
                }
            }

            validarYCalcularDespacho(
                montoCompra, distanciaKm, onError = onError, onSuccess = onSuccess
            )
        }, modifier = Modifier.fillMaxWidth()
    ) {
        Text("Calcular despacho")
    }
}