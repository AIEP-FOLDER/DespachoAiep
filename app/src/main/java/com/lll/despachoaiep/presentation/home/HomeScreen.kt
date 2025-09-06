// java/com/lll/despachoaiep/presentation/home/HomeScreen.kt
package com.lll.despachoaiep.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.R
import com.lll.despachoaiep.ui.theme.Black
import com.lll.despachoaiep.utils.calcularDespacho
import com.lll.despachoaiep.utils.validarYCalcularDespacho


@Composable
fun HomeScreen(
    auth: FirebaseAuth, onLogout: () -> Unit
) {
    var montoCompra by remember { mutableStateOf("") }
    var distanciaKm by remember { mutableStateOf("") }
    var incluyeCongelados by remember { mutableStateOf(false) }


    var direccion by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var resultadoDespacho by remember {
        mutableStateOf<Int?>(null)
    }

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(
                painter = painterResource(id = R.drawable.arrowback),
                contentDescription = "Volver",
                tint = Color.White,
                modifier = Modifier
                    .padding(vertical = 36.dp)
                    .size(32.dp)

            )
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bienvenido, Matias",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Cerrar session",
                tint = Color.White,
                modifier = Modifier
                    .padding(vertical = 36.dp)
                    .size(32.dp)
                    .clickable {
                        auth.signOut()
                        onLogout()
                    }
                    .align(Alignment.CenterVertically)
            )

        }
        Text(
            "Despacho AIEP", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = montoCompra,
            onValueChange = { montoCompra = it },
            label = { Text("Monto de compra") },
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
            value = distanciaKm,
            onValueChange = { distanciaKm = it },
            label = { Text("Distancia (km)") },
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = incluyeCongelados,
                onCheckedChange = { incluyeCongelados = it }
            )
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
                validarYCalcularDespacho(
                    montoCompra,
                    distanciaKm,
                    onError = {
                        showError = true
                        errorMessage = it
                        resultadoDespacho = null
                    },
                    onSuccess = {
                        showError = false
                        resultadoDespacho = it
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular despacho")
        }

        if (showError) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp
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

@Preview
@Composable
fun HomeScreen(

) {

    var montoCompra by remember { mutableStateOf("") }

    var distanciaKm by remember { mutableStateOf("") }

    var incluyeCongelados by remember { mutableStateOf(false) }

    var direccion by remember { mutableStateOf("") }

    var contacto by remember { mutableStateOf("") }

    var resultadoDespacho by remember {
        mutableStateOf<Int?>(null)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrowback),
                contentDescription = "Volver",
                tint = Color.White,
                modifier = Modifier
                    .padding(vertical = 36.dp)
                    .size(32.dp)

            )

            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bienvenido, Matias",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Icono de cerrar sesión a la derecha
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Cerrar session",
                tint = Color.White,
                modifier = Modifier
                    .padding(vertical = 36.dp)
                    .size(32.dp)
            )

        }

        Text(
            "Despacho AIEP", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = montoCompra,
            onValueChange = { montoCompra = it },
            label = { Text("Monto de compra") },
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
            value = distanciaKm,
            onValueChange = { distanciaKm = it },
            label = { Text("Distancia (km)") },
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = incluyeCongelados,
                onCheckedChange = { incluyeCongelados = it }
            )
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
                val monto = montoCompra.toIntOrNull()
                val distancia = distanciaKm.toDoubleOrNull()
                if (monto != null && distancia != null) {
                    resultadoDespacho = calcularDespacho(monto, distancia)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular despacho")
        }
        resultadoDespacho?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Costo de despacho: $${it}", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.weight(1f))
        Text("Versión cliente: Oreo", color = Color.LightGray, fontSize = 12.sp)


    }
}


