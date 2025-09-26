package com.lll.despachoaiep.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lll.despachoaiep.model.EnvioDespacho
import com.lll.despachoaiep.model.EstadoEntrega
import com.lll.despachoaiep.model.TipoAnimacionLottie
import com.lll.despachoaiep.model.UbicacionGps
import com.lll.despachoaiep.ui.theme.Green
import com.lll.despachoaiep.ui.theme.ShapeButton
import com.lll.despachoaiep.utils.guardarEnvioCompleto
import com.lll.despachoaiep.utils.guardarUbicacionEnFirebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewResultadosSheet(
    resultadoDespacho: Int,
    montoCompraInt: Int,
    direccionEntrega: String,
    contactoEntrega: String,
    ubicacionActual: UbicacionGps,
    incluyeCongelados: Boolean,
    productosSeleccionados: List<Int>,
    estadoEntrega: EstadoEntrega,
    showSheet: Boolean,
    onDismiss: () -> Unit,
    onAnimacionLottie: (TipoAnimacionLottie) -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()


    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Resumen del despacho",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                ListItem(
                    headlineContent = { Text("Dirección de envio") },
                    supportingContent = { Text(direccionEntrega) },
                    leadingContent = {
                        Icon(
                            Icons.Default.Receipt, contentDescription = null
                        )
                    }
                )
                ListItem(
                    headlineContent = { Text("Numero de contacto") },
                    supportingContent = { Text(contactoEntrega) },
                    leadingContent = {
                        Icon(
                            Icons.Default.Receipt, contentDescription = null
                        )
                    }
                )
                if (incluyeCongelados) {
                    ListItem(
                        headlineContent = { Text("Incluye congelados") },
                        supportingContent = { Text("Si") },
                        leadingContent = {
                            Icon(
                                Icons.Default.Receipt, contentDescription = null
                            )
                        }
                    )
                }
                ListItem(
                    headlineContent = { Text("Costo de despacho") },
                    supportingContent = { Text("$${resultadoDespacho}") },
                    leadingContent = {
                        Icon(
                            Icons.Default.Receipt, contentDescription = null
                        )
                    }
                )
                ListItem(
                    headlineContent = { Text("Total estimado") },
                    supportingContent = { Text("$${resultadoDespacho + montoCompraInt}") },
                    leadingContent = {
                        Icon(
                            Icons.Default.Money, contentDescription = null
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {

                        // primero quiero validar que tenga la ubicacion, nombre usuario y correo
                        if (
                            ubicacionActual.latitud != 0.0 &&
                            ubicacionActual.longitud != 0.0 &&
                            ubicacionActual.nombreUsuario.isNotBlank() &&
                            ubicacionActual.correo.isNotBlank()
                        ) {
                            // aqui utilizo mi modelo "Envio despacho" para crear el objeto

                            val envio = EnvioDespacho(
                                nombreUsuario = ubicacionActual.nombreUsuario,
                                correo = ubicacionActual.correo,
                                direccionEntrega = direccionEntrega,
                                contactoEntrega = contactoEntrega,
                                latitud = ubicacionActual.latitud,
                                longitud = ubicacionActual.longitud,
                                incluyeCongelados = incluyeCongelados,
                                productosSeleccionados = productosSeleccionados,
                                costoDespacho = resultadoDespacho,
                                totalEstimado = resultadoDespacho + montoCompraInt,
                                estadoEntrega = estadoEntrega.name
                            )
                            guardarUbicacionEnFirebase(ubicacionActual)
                            guardarEnvioCompleto(envio)

                            // ✅ Cerrar el sheet después de guardar
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                onDismiss()
                                onAnimacionLottie(TipoAnimacionLottie.Confirmacion)
                            }

                        } else {
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                onDismiss()
                            }
                            // Opcional: mostrar alerta visual
                            Log.e("Despacho", "Ubicación no disponible. No se guardó.")
                            onAnimacionLottie(TipoAnimacionLottie.Error)


                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green,
                        contentColor = ShapeButton
                    )
                ) {
                    Text("Enviar pedido")
                }
                Button(
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            onDismiss()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}