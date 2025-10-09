package com.lll.despachoaiep.presentation.mapaview

import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.R
import com.lll.despachoaiep.R.*
import com.lll.despachoaiep.datos.network.OpenRouteServiceClient
import com.lll.despachoaiep.presentation.despacho.DespachoViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.infowindow.InfoWindow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaPedidoScreen(
    latitud_bodega: Double = -41.317876, // Puerto Varas por defecto
    longitud_bodega: Double = -72.982753,
    estadoPedido: String = "En camino",
    viewModel: DespachoViewModel = viewModel(),
) {
    val usuarioActual = FirebaseAuth.getInstance().currentUser
    val uid = usuarioActual?.uid ?: return

    val context = LocalContext.current

    // Cargar configuración de OSMDroid
    Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", 0))

    // necesito obtener la ubicacion de la plaza de armas de puerto varas
    // Latitud y longitud de la bodega -41.317876, -72.982753
    //-------------
    LaunchedEffect(uid) {
        viewModel.cargarPedidos(uid)
    }
    val pedidosEnReparto = viewModel.pedidosEnReparto
    var pedidoSeleccionadoIndex by remember { mutableIntStateOf(0) }
    val pedidoActual = pedidosEnReparto.getOrNull(pedidoSeleccionadoIndex)

    //-------------
    // Estados De la distacia y duracion
    var distanciaKm by remember { mutableStateOf<Double?>(null) }
    var duracionMin by remember { mutableStateOf<Double?>(null) }


    Column {
        /*
        VISTA DEL MAPA CON SU RESPECTIVO MARKER
         */
        if (pedidosEnReparto.isNotEmpty() && pedidoActual != null) {
            val pedidoActual = pedidosEnReparto[pedidoSeleccionadoIndex]

            // obtener la ubicacion del usuario actual en gps
            val latCliente = pedidoActual.envio.latitud
            val lonCliente = pedidoActual.envio.longitud
            Box(modifier = Modifier.fillMaxSize()) {
                key(pedidoSeleccionadoIndex) {
                    MapaPedidoView(
                        latitudBodega = latitud_bodega,
                        longitudBodega = longitud_bodega,
                        latCliente = pedidoActual.envio.latitud,
                        lonCliente = pedidoActual.envio.longitud,
                        pedidoActual = pedidoActual.envio,
                        onRutaCalculada = { distancia, duracion ->
                            distanciaKm = distancia
                            duracionMin = duracion
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )

                }
                // BOTON PARA CAMBIAR EL PEDIDO
                BotonCambioPedido(
                    pedidoSeleccionadoIndex = pedidoSeleccionadoIndex,
                    totalPedidos = pedidosEnReparto.size,
                    onAnterior = { if (pedidoSeleccionadoIndex > 0) pedidoSeleccionadoIndex-- },
                    onSiguiente = { if (pedidoSeleccionadoIndex < pedidosEnReparto.size - 1) pedidoSeleccionadoIndex++ },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                )
                if (distanciaKm != null && duracionMin != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("🛣️ Distancia: %.2f km".format(distanciaKm!!), color = Color.Black)
                                Text("⏱️ Duración: %.1f min".format(duracionMin!!), color = Color.Black)
                            }
                        }
                    }
                }


            }

        } else {
            // Si el listado de pedidos está vacío, muestra un mensaje
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = "No tienes pedidos asignados actualmente.",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.Asset("maps_empty.json")
                    )
                    val progress by animateLottieCompositionAsState(
                        composition,
                        iterations = LottieConstants.IterateForever
                    )

                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    )

                }

            }

        }

    }
}