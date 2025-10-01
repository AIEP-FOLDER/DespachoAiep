package com.lll.despachoaiep.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun ViewMontosYDistancia(
    montoCompra: String,
    onMontoChange: (String) -> Unit,
    distanciaKm: String,
    onDistanciaChange: (String) -> Unit,
    cargandoUbicacion: Boolean,
    temperaturaCamion: Double
) {





    // monto de compra
    TextField(
        value = montoCompra,
        onValueChange = {
            onMontoChange(it)
        },
        label = { Text("Monto de compra") },
        singleLine = true,
        enabled = false,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = textFieldColorsElegantes()
    )

    Spacer(modifier = Modifier.height(12.dp))

    // ubicacion / distancia
    if (cargandoUbicacion) {
        Spacer(modifier = Modifier.height(12.dp))
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))
    } else {
        TextField(
            value = distanciaKm,
            onValueChange = onDistanciaChange,
            label = { Text("Distancia (km)") },
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColorsElegantes()

        )
    }


    Spacer(modifier = Modifier.height(12.dp))
    // barra para visualizar la temperatura
    BarraTemperatura(temperaturaCamion)
    Spacer(modifier = Modifier.height(12.dp))
    // temperatura del camion
    TextField(
        value = temperaturaCamion.toString(),
        onValueChange = {},
        label = { Text("Temperatura (°C)") },
        singleLine = true,
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = textFieldColorsElegantes()
    )
    Spacer(modifier = Modifier.height(12.dp))
}




