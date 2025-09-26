package com.lll.despachoaiep.presentation.perfil


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.model.EstadoEntrega
import com.lll.despachoaiep.presentation.despacho.DespachoViewModel
import com.lll.despachoaiep.ui.theme.Black

@Composable
fun PerfilScreen(
    auth: FirebaseAuth,
    onLogout: () -> Unit,
    viewModel: DespachoViewModel = viewModel()

) {
    val usuarioActual = FirebaseAuth.getInstance().currentUser
    val uid = usuarioActual?.uid ?: return


    val nombreUsuario = usuarioActual.displayName?.takeIf { it.isNotBlank() } ?: "Usuario"

    val defaultAvatarUrl = "https://cdn-icons-png.flaticon.com/512/149/149071.png"
    val imgUsuario = usuarioActual.photoUrl?.toString() ?: defaultAvatarUrl

    val pedidos = viewModel.pedidos

    val scrollState = rememberScrollState()

    var estadoSeleccionado by remember { mutableStateOf(EstadoEntrega.Reparto) }
    val pedidosFiltrados = pedidos.filter { it.estado == estadoSeleccionado }

    LaunchedEffect(uid) {
        viewModel.cargarPedidos(uid)
    }
    if (pedidosFiltrados.isEmpty()) {
        Text(
            "No hay pedidos en este estado",
            color = Color.LightGray,
            modifier = Modifier.padding(16.dp)
        )
    }


    /*
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Black),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                NavPerfilScreen(imgUsuario, nombreUsuario, nombreUsuario)
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Vista dinamica de reparto
            SegmentoEstadoEntrega(
                estadoSeleccionado = estadoSeleccionado,
                onEstadoChange = { estadoSeleccionado = it }
            )
            LazyColumn {
                items(pedidosFiltrados) { pedido ->
                    CardPedido(pedido, uid, viewModel)
                }
            }

            // Empuja el botón hacia abajo
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    auth.signOut()
                    onLogout()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 32.dp,
                        vertical = 16.dp
                    )
            ) {
                Text("Cerrar sesión", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))


        }
    }
*/
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    NavPerfilScreen(
                        imgUsuario,
                        nombreUsuario,
                        nombreUsuario,
                        onLogout = onLogout,
                        auth = auth
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                SegmentoEstadoEntrega(
                    estadoSeleccionado = estadoSeleccionado,
                    onEstadoChange = { estadoSeleccionado = it }
                )
            }

            if (pedidosFiltrados.isEmpty()) {
                item {
                    Text(
                        "No hay pedidos en este estado",
                        color = Color.LightGray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(pedidosFiltrados) { pedido ->
                    CardPedido(pedido, uid, viewModel)
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }


}
