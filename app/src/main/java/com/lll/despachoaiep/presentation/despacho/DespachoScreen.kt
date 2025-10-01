package com.lll.despachoaiep.presentation.despacho

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.datos.FirebaseTemperaturaDataSource
import com.lll.despachoaiep.model.EstadoEntrega
import com.lll.despachoaiep.model.TipoAnimacionLottie
import com.lll.despachoaiep.model.UbicacionGps
import com.lll.despachoaiep.presentation.components.BotonCalculoDespacho
import com.lll.despachoaiep.presentation.components.FormularioEntrega
import com.lll.despachoaiep.presentation.components.ViewMontosYDistancia
import com.lll.despachoaiep.presentation.components.ViewResultadosSheet
import com.lll.despachoaiep.presentation.components.topBar.CarritoViewModel
import com.lll.despachoaiep.presentation.home.HomeDestination
import com.lll.despachoaiep.presentation.productos.ProductosViewModel
import com.lll.despachoaiep.ui.theme.Black
import com.lll.despachoaiep.utils.AnimacionLottiePantallaCompleta
import com.lll.despachoaiep.utils.CatalogoProductosBurbuja
import com.lll.despachoaiep.utils.calcularDistanciaHaversine
import com.lll.despachoaiep.utils.obtenerUbicacionActual
import kotlinx.coroutines.delay




@Composable
fun DespachoScreen(
    auth: FirebaseAuth,
    onLogout: () -> Unit,
    carritoViewModel: CarritoViewModel,
    viewModel: ProductosViewModel = viewModel()
) {

    // estado producto seleccionado/des
    val productosSeleccionados = remember { mutableStateListOf<Int>() } // guarda los IDs


    // estado de distacia
    val context = LocalContext.current

    //----------------------
    val usuarioActual = FirebaseAuth.getInstance().currentUser
    val nombreUsuario = usuarioActual?.displayName ?: "Usuario"

    // recuperar imagen
    val imagenUsuario = usuarioActual?.photoUrl?.toString()


    var montoCompraInt by remember { mutableIntStateOf(0) }
    val montoCompra = montoCompraInt.toString()


    var distanciaKm by remember { mutableStateOf("") }
    // variable booleana para saber si incluye congelados
    var incluyeCongelados by remember { mutableStateOf(false) }


    var direccion by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var resultadoDespacho by remember {
        mutableStateOf<Int?>(null)
    }

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }


    val scrollState = rememberScrollState()
    var cargandoUbicacion by remember { mutableStateOf(true) }

    // navbootom
    val navController = rememberNavController()
    var selectedRoute by rememberSaveable { mutableStateOf(HomeDestination.Productos.route) }

    // productos traidos de la base de datos
    val productos by viewModel.productos.collectAsState()

    var showResultadosSheet by remember { mutableStateOf(false) }

    // ubicacion actual del usuario
    var ubicacionActual by remember { mutableStateOf<UbicacionGps?>(null) }

    var estadoEntrega by remember { mutableStateOf(EstadoEntrega.Reparto) }


    // estado de animacion lottie
    var mostrarAnimacionConfirmacion by remember { mutableStateOf(false) }
    var mostrarAnimacionError by remember { mutableStateOf(false) }
    var tipoAnimacionLottie by remember { mutableStateOf(TipoAnimacionLottie.Ninguna) }

    /*
    Obtener los valores de temperatura de firebase
     */
    val temperaturaCamion = remember { mutableStateOf<Double?>(null) }
    val fuenteTemperatura = remember { FirebaseTemperaturaDataSource() }



    // Launch para obtener la ubicacion siempre actualizada
    LaunchedEffect(Unit) {
        obtenerUbicacionActual(context = context, onSuccess = { location ->
            val distancia = calcularDistanciaHaversine(
                lat1 = location.latitude, lon1 = location.longitude,
                // lat bodega y log bodega en plaza de armas
                lat2 = -41.317831, // Plaza de Armas Puerto Varas
                lon2 = -72.982737
            )
            // -41.317831, -72.982737
            distanciaKm = "%.2f".format(distancia) // actualiza el TextField
            cargandoUbicacion = false

            val usuario = FirebaseAuth.getInstance().currentUser
            val nombre = usuario?.displayName ?: "Sin nombre"
            val correo = usuario?.email ?: "Sin correo"


            // Guardar en Firebase
            ubicacionActual = UbicacionGps(
                latitud = location.latitude,
                longitud = location.longitude,
                nombreUsuario = nombre,
                correo = correo

            )
            //guardarUbicacionEnFirebase(ubicacion)


        }, onError = {
            Log.e("Ubicación [onError]", it)
            cargandoUbicacion = false
        })
    }

    // desaparecer la animacion
    LaunchedEffect(mostrarAnimacionConfirmacion) {
        if (mostrarAnimacionConfirmacion) {
            delay(5000) // espera 6 segundos
            mostrarAnimacionConfirmacion = false
        }
    }

    // obtener la temperatura del camion siempre actualizada

    LaunchedEffect(Unit) {
        fuenteTemperatura.obtenerUltimaLectura { lectura ->
            temperaturaCamion.value = lectura?.valorCelsius
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


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
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {


                ViewMontosYDistancia(
                    montoCompra = montoCompra,
                    onMontoChange = { montoCompraInt = it.toIntOrNull() ?: montoCompraInt },
                    distanciaKm = distanciaKm,
                    onDistanciaChange = { distanciaKm = it },
                    cargandoUbicacion = cargandoUbicacion,
                    temperaturaCamion = temperaturaCamion.value ?: 0.0
                )



                FormularioEntrega(
                    direccion = direccion,
                    contacto = contacto,
                    incluyeCongelados = incluyeCongelados,
                    onDireccionChange = { direccion = it },
                    onContactoChange = { contacto = it },
                    onCongeladosChange = { incluyeCongelados = it }
                )


                Spacer(modifier = Modifier.height(24.dp))

                BotonCalculoDespacho(
                    productosSeleccionados = productosSeleccionados,
                    incluyeCongelados = incluyeCongelados,
                    temperaturaCamion = temperaturaCamion.value ?: 0.0,
                    montoCompra = montoCompra,
                    distanciaKm = distanciaKm,
                    montoCompraInt = montoCompraInt,
                    onError = {
                        showError = true
                        errorMessage = it
                        resultadoDespacho = null
                    },
                    onSuccess = {
                        showError = false
                        resultadoDespacho = it
                        showResultadosSheet = true
                    },
                    direccionDespacho = direccion,
                    contactoDespacho = contacto
                )
                Spacer(modifier = Modifier.height(24.dp))




                if (showError) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = errorMessage, color = Color.Red, fontSize = 14.sp
                    )
                }

                ViewResultadosSheet(
                    resultadoDespacho = resultadoDespacho ?: 0,
                    montoCompraInt = montoCompraInt,
                    showSheet = showResultadosSheet,
                    onDismiss = { showResultadosSheet = false },
                    direccionEntrega = direccion,
                    contactoEntrega = contacto,
                    incluyeCongelados = incluyeCongelados,
                    ubicacionActual = ubicacionActual ?: UbicacionGps(),
                    productosSeleccionados = productosSeleccionados.toList(),
                    estadoEntrega = estadoEntrega,
                    onAnimacionLottie = { tipoAnimacionLottie = it }
                )


                Spacer(modifier = Modifier.weight(1f))
                Text("Versión cliente: Oreo", color = Color.LightGray, fontSize = 12.sp)
                Spacer(modifier = Modifier.weight(1f))
            }

        }
        // Animación Lottie en pantalla completa
        AnimacionLottiePantallaCompleta(
            tipo = tipoAnimacionLottie,
            onOcultar = { tipoAnimacionLottie = TipoAnimacionLottie.Ninguna }
        )


    }


}



