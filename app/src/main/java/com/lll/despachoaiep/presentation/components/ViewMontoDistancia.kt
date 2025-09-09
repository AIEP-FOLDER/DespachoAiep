package com.lll.despachoaiep.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.foundation.layout.*
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
    cargandoUbicacion: Boolean
) {
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
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.DarkGray,
            unfocusedContainerColor = Color.Gray,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.LightGray,
            cursorColor = Color.White
        )
    )

    Spacer(modifier = Modifier.height(12.dp))

    if (cargandoUbicacion) {
        Spacer(modifier = Modifier.height(12.dp))
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))
    }

    TextField(
        value = distanciaKm,
        onValueChange = onDistanciaChange,
        label = { Text("Distancia (km)") },
        singleLine = true,
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.DarkGray,
            unfocusedContainerColor = Color.Gray,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.LightGray,
            cursorColor = Color.White
        )
    )

    Spacer(modifier = Modifier.height(12.dp))
}