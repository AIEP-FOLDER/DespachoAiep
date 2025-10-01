package com.lll.despachoaiep.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BarraTemperatura(temperatura: Double) {
    val minTemp = -40.0
    val maxTemp = 125.0

    val porcentaje = ((temperatura - minTemp) / (maxTemp - minTemp)).coerceIn(0.0, 1.0)

    val colorTemperatura = when {
        temperatura < 0 -> Color.Cyan
        temperatura in 0.0..5.0 -> Color.Blue
        temperatura in 6.0..25.0 -> Color.Green
        else -> Color.Red
    }




    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Thermostat,
            contentDescription = "Temperatura",
            tint = colorTemperatura,
            modifier = Modifier
                .size(35.dp)
                .padding(start = 8.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f) // ocupa el resto del espacio
                .fillMaxHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Gray.copy(alpha = 0.2f))
        ) {


            // Barra de temperatura actual
            Box(
                modifier = Modifier
                    .fillMaxWidth(porcentaje.toFloat())
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorTemperatura)
            )

        }
    }


}
