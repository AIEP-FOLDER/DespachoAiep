// java/com/lll/despachoaiep/presentation/home/HomeScreen.kt
package com.lll.despachoaiep.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem


import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState

import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

import com.lll.despachoaiep.presentation.components.topBar.TopBarContainer
import com.lll.despachoaiep.utils.calcularDistanciaHaversine
import com.lll.despachoaiep.utils.obtenerUbicacionActual


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

    // recuperar imagen
    val imagenUsuario = usuarioActual?.photoUrl?.toString()


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


    val scrollState = rememberScrollState()
    var cargandoUbicacion by remember { mutableStateOf(true) }

    // navbootom
    val navController = rememberNavController()
    //var selectedRoute by rememberSaveable { mutableStateOf(HomeDestination.Despacho.route) }




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
                cargandoUbicacion = false
            },
            onError = {
                Log.e("Ubicación [onError]", it)
                cargandoUbicacion = false
            }
        )
    }



    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopBarContainer(
                nombreUsuario = nombreUsuario,
                auth = auth,
                onLogout = onLogout
            )

        },
        bottomBar = {
            NavigationBar {
                HomeDestination.all.filterNotNull().forEach { destination ->
                    NavigationBarItem(
                        //selected = selectedRoute == destination.route,
                        selected = currentRoute == destination.route,

                        onClick = {
                            /*
                            selectedRoute = destination.route
                            navController.navigate(destination.route) {
                                launchSingleTop = true
                            }
                            */

                            navController.navigate(destination.route) {
                                launchSingleTop = true
                            }



                        },
                        icon = { Icon(destination.icon, contentDescription = destination.label) },
                        label = { Text(destination.label) }
                    )
                }

            }
        }


    ) { innerPadding ->
        /*
        BottomBarContainer(
            nombreUsuario = nombreUsuario,
            imagenUsuario = imagenUsuario
        )
        */

        HomeNavHost(
            navController = navController,
            auth = auth,
            onLogout = onLogout,
            modifier = Modifier.padding(innerPadding)
        )
    }

}




