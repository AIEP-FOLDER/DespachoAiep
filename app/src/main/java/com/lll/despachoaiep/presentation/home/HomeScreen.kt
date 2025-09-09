// java/com/lll/despachoaiep/presentation/home/HomeScreen.kt
package com.lll.despachoaiep.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions


import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.datos.productos
import com.lll.despachoaiep.presentation.components.ViewNavbar
import com.lll.despachoaiep.ui.theme.Black
import com.lll.despachoaiep.utils.CatalogoProductosBurbuja
import com.lll.despachoaiep.utils.calcularDistanciaHaversine
import com.lll.despachoaiep.utils.convertirAGrados
import com.lll.despachoaiep.utils.obtenerUbicacionActual
import com.lll.despachoaiep.utils.validarYCalcularDespacho


@Composable
fun HomeScreen(
    auth: FirebaseAuth, onLogout: () -> Unit
) {

    // estado producto seleccionado/des
    val productosSeleccionados = remember { mutableStateListOf<Int>() } // guarda los IDs


    // estado de distacia
    var distanciaCalculadaKm by remember { mutableStateOf<Double?>(null) }
    val context = LocalContext.current

    //----------------------
    val usuarioActual = FirebaseAuth.getInstance().currentUser
    val nombreUsuario = usuarioActual?.displayName ?: "Usuario"


    var montoCompraInt by remember { mutableIntStateOf(0) }
    val montoCompra = montoCompraInt.toString()


    var distanciaKm by remember { mutableStateOf("") }
    var incluyeCongelados by remember { mutableStateOf(false) }


    var direccion by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var resultadoDespacho by remember {
        mutableStateOf<Int?>(null)
    }

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        obtenerUbicacionActual(
            context = context,
            onSuccess = { location ->
                val distancia = calcularDistanciaHaversine(
                    lat1 = location.latitude,
                    lon1 = location.longitude,
                    // lat bodega y log bodega en plaza de armas
                    lat2 = -41.317831, // Plaza de Armas Puerto Varas
                    lon2 = -72.982737
                )
                // -41.317831, -72.982737
                distanciaCalculadaKm = distancia
                distanciaKm = "%.2f".format(distancia) // actualiza el TextField
            },
            onError = {
                Log.e("Ubicación [onError]", it)
            }
        )
    }






    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ViewNavbar(
            nombreUsuario = nombreUsuario,
            auth = auth,
            onLogout = onLogout
        )

        CatalogoProductosBurbuja(
            productos = productos,
            productosSeleccionados = productosSeleccionados,
            onToggleProducto = { producto ->
                if (productosSeleccionados.contains(producto.id)) {
                    productosSeleccionados.remove(producto.id)
                    montoCompraInt -= producto.precio
                } else {
                    productosSeleccionados.add(producto.id)
                    montoCompraInt += producto.precio
                }
            }

        )


        TextField(
            value = montoCompra,
            onValueChange = {
                // Solo actualiza si el texto es numérico
                montoCompraInt = it.toIntOrNull() ?: montoCompraInt
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

        TextField(
            value = distanciaKm,
            onValueChange = { distanciaKm = it },
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
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = incluyeCongelados, onCheckedChange = { incluyeCongelados = it })
            Text("¿Incluye productos congelados?", color = Color.White)
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección de entrega") },
            singleLine = true,
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
        TextField(
            value = contacto,
            onValueChange = { contacto = it },
            label = { Text("Correo o teléfono") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.Gray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                validarYCalcularDespacho(montoCompra, distanciaKm, onError = {
                    showError = true
                    errorMessage = it
                    resultadoDespacho = null
                }, onSuccess = {
                    showError = false
                    resultadoDespacho = it
                    val radianesEjemplo = 1.57
                    val grados = convertirAGrados(radianesEjemplo)
                })
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular despacho")
        }
        Spacer(modifier = Modifier.height(24.dp))




        if (showError) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = errorMessage, color = Color.Red, fontSize = 14.sp
            )
        }


        resultadoDespacho?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Costo de despacho: $${it}", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.weight(1f))
        Text("Versión cliente: Oreo", color = Color.LightGray, fontSize = 12.sp)


    }
}




