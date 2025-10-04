package com.lll.despachoaiep.presentation.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun RangoTemperaturasView(
    minTemp: String,
    maxTemp: String,
    onMinChange: (String) -> Unit,
    onMaxChange: (String) -> Unit,
    onGuardar: () -> Unit,
    onEliminar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Configuración de rangos de temperatura", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = minTemp,
            onValueChange = onMinChange,
            label = { Text("Temperatura mínima (°C)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = maxTemp,
            onValueChange = onMaxChange,
            label = { Text("Temperatura máxima (°C)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = onGuardar, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar rangos")
        }

        Button(
            onClick = onEliminar,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("Eliminar configuración")
        }

    }
}