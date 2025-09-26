package com.lll.despachoaiep.presentation.perfil

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lll.despachoaiep.model.EstadoEntrega
import com.lll.despachoaiep.model.PedidoConEstado
import com.lll.despachoaiep.presentation.despacho.DespachoViewModel
import com.lll.despachoaiep.ui.theme.BackgroundButton
import com.lll.despachoaiep.ui.theme.Green
import com.lll.despachoaiep.ui.theme.ShapeButton


@Composable
fun SegmentoEstadoEntrega(
    estadoSeleccionado: EstadoEntrega,
    onEstadoChange: (EstadoEntrega) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    // entregado /reparto / error
    val options = EstadoEntrega.entries


    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        options.forEachIndexed { index, estado ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { onEstadoChange(estado) },
                selected = estadoSeleccionado == estado,
                label = { Text(estado.name) }
            )
        }
    }

}


@Composable
fun CardPedido(
    pedido: PedidoConEstado,
    uid: String,
    viewModel: DespachoViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp, vertical = 2.dp)
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ListItem(
                    headlineContent = { Text("Dirección de envío") },
                    supportingContent = { Text(pedido.envio.direccionEntrega) },
                    leadingContent = {
                        Icon(Icons.Default.Receipt, contentDescription = null)
                    }
                )
                ListItem(
                    headlineContent = { Text("Totales") },
                    supportingContent = {
                        Column {
                            Text(
                                "Costo de despacho: $${pedido.envio.costoDespacho}",
                                color = Color.White
                            )
                            Text(
                                "Total estimado: $${pedido.envio.totalEstimado}",
                                color = Color.White
                            )
                        }
                    }
                )
                // Botón para cambiar estado
                if (pedido.estado == EstadoEntrega.Reparto) {
                    Button(
                        onClick = {
                            viewModel.actualizarEstado(
                                uid,
                                pedido.pedidoId,
                                EstadoEntrega.Entregado
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green,
                            contentColor = ShapeButton
                        )

                    ) {
                        Text("Marcar como entregado")
                    }
                }


            }
        }
    }
}