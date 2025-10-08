package com.lll.despachoaiep.presentation.mapaview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BotonCambioPedido(
    pedidoSeleccionadoIndex: Int,
    totalPedidos: Int,
    onAnterior: () -> Unit,
    onSiguiente: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onAnterior,
            enabled = pedidoSeleccionadoIndex > 0
        ) {
            Text("← Anterior")
        }
        Spacer(Modifier.width(8.dp))
        Button(
            onClick = onSiguiente,
            enabled = pedidoSeleccionadoIndex < totalPedidos - 1
        ) {
            Text("Siguiente →")
        }
    }
}