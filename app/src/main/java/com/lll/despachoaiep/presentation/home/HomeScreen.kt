// java/com/lll/despachoaiep/presentation/home/HomeScreen.kt
package com.lll.despachoaiep.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.presentation.components.topBar.CarritoViewModel
import com.lll.despachoaiep.presentation.components.topBar.TopBarContainer
import com.lll.despachoaiep.ui.theme.Black
import com.lll.despachoaiep.utils.rememberRolUsuario


@Composable
fun HomeScreen(
    auth: FirebaseAuth, onLogout: () -> Unit
) {
    //----------------------
    val usuarioActual = FirebaseAuth.getInstance().currentUser
    val nombreUsuario = usuarioActual?.displayName ?: "Usuario"

    // navbootom
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val carritoViewModel: CarritoViewModel = viewModel()

    //-----------------------------------------
    // DETECTAR EL ROL DEL USUARIO REGISTRADO
    val rolUsuario = rememberRolUsuario()
    //-----------------------------------------


    Scaffold(
        topBar = {
            TopBarContainer(
                nombreUsuario = nombreUsuario,
                auth = auth,
                onLogout = onLogout,
                carritoViewModel = carritoViewModel
            )

        },
        bottomBar = {
            NavigationBar(
                containerColor = Black,
                tonalElevation = 0.dp
            ) {
                val destinos = if (rolUsuario == "admin") {
                    HomeDestination.all + HomeDestination.adminOnly
                } else {
                    HomeDestination.all
                }

                destinos.forEach { destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.label,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = { Text(destination.label, fontSize = 12.sp) }
                    )
                }

            }
        }


    ) { innerPadding ->
        HomeNavHost(
            navController = navController,
            auth = auth,
            onLogout = onLogout,
            modifier = Modifier.padding(innerPadding),
            rolUsuario = rolUsuario
        )
    }

}




