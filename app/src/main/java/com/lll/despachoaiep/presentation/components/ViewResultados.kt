package com.lll.despachoaiep.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ViewResultados(
    resultadoDespacho: Int,
    montoCompraInt: Int
) {
    Spacer(modifier = Modifier.height(16.dp))
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,

            ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Resumen del despacho",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(
                    bottom = 10.dp, top = 10.dp
                )
            )



            Text(
                text = "🧾 Costo de despacho: $${resultadoDespacho}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            Text(
                text = "💰 Total estimado: $${resultadoDespacho + montoCompraInt}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

        }


    }
    Spacer(modifier = Modifier.height(16.dp))
}