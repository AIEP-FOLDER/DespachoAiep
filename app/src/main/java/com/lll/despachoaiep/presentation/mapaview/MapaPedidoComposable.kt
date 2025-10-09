package com.lll.despachoaiep.presentation.mapaview

import android.util.Log
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.lll.despachoaiep.R
import com.lll.despachoaiep.datos.network.OpenRouteServiceClient
import com.lll.despachoaiep.model.EnvioDespacho
import com.lll.despachoaiep.utils.calcularDespacho
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.infowindow.InfoWindow

@Composable
fun MapaPedidoView(
    latitudBodega: Double,
    longitudBodega: Double,
    latCliente: Double,
    lonCliente: Double,
    pedidoActual: EnvioDespacho,
    onRutaCalculada: (Double, Double) -> Unit,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { ctx ->
            val mapView = MapView(ctx)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setMultiTouchControls(true)
            mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

            val puntoBodega = GeoPoint(latitudBodega, longitudBodega)
            val puntoCliente = GeoPoint(latCliente, lonCliente)

            val controller = mapView.controller
            controller.setZoom(15.0)
            controller.setCenter(puntoCliente)


            /*
            MARKER: Bodega
             */
            val markerBodega = Marker(mapView).apply {
                position = puntoBodega
                title = "Bodega principal"
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }
            mapView.overlays.add(markerBodega)


            val cliente = OpenRouteServiceClient()
            cliente.obtenerRuta(
                origenLat = latitudBodega,
                origenLon = longitudBodega,
                destinoLat = latCliente,
                destinoLon = lonCliente,
                onSuccess = { puntos, distanciaKm, duracionMin ->
                    val ruta = Polyline().apply {
                        outlinePaint.color = android.graphics.Color.BLUE
                        outlinePaint.strokeWidth = 5f
                        setPoints(puntos.map { GeoPoint(it.first, it.second) })
                    }
                    onRutaCalculada(distanciaKm, duracionMin)

                    val costoDespacho = calcularDespacho(pedidoActual.totalEstimado, distanciaKm)

                    /*
                     MARKER: Cliente
                      */
                    val markerCliente = Marker(mapView).apply {
                        position = puntoCliente
                        title = "Cliente: ${pedidoActual.nombreUsuario}"
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    }

                    val infoWindow: InfoWindow = object : InfoWindow(R.layout.info_window_cliente, mapView) {
                        override fun onOpen(item: Any?) {
                            val view = mView
                            view.findViewById<TextView>(R.id.nombreCliente).text =
                                "Cliente: ${pedidoActual.nombreUsuario}"
                            view.findViewById<TextView>(R.id.direccionEntrega).text =
                                "📍 Dirección: ${pedidoActual.direccionEntrega}"
                            view.findViewById<TextView>(R.id.contactoCliente).text =
                                "📞 Contacto: ${pedidoActual.contactoEntrega}"
                            view.findViewById<TextView>(R.id.totalPedido).text =
                                "💰 Total: ${pedidoActual.totalEstimado} CLP"
                            view.findViewById<TextView>(R.id.despachoPedido).text =
                                "🛵 Despacho: $costoDespacho CLP"
                        }

                        override fun onClose() {}
                    }

                    markerCliente.infoWindow = infoWindow
                    markerCliente.showInfoWindow()
                    mapView.overlays.add(markerCliente)
                    mapView.overlays.add(ruta)
                    mapView.invalidate()
                },
                onError = { error ->
                    Log.e("MapaPedidoView", "Error al obtener ruta: $error")
                }
            )

            mapView
        },
        modifier = modifier
    )
}