package com.lll.despachoaiep.presentation.home

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.presentation.despacho.DespachoScreen
import com.lll.despachoaiep.presentation.perfil.PerfilScreen
import com.lll.despachoaiep.presentation.productos.ProductosScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lll.despachoaiep.presentation.admin.PantallaAdmin
import com.lll.despachoaiep.presentation.components.topBar.CarritoViewModel

sealed class HomeDestination(val route: String, val icon: ImageVector, val label: String) {
    object Productos : HomeDestination("productos", Icons.Default.ShoppingCart, "Productos")
    object Despacho : HomeDestination("despacho", Icons.Default.LocalShipping, "Despacho")
    object Perfil : HomeDestination("perfil", Icons.Default.Person, "Perfil")

    // exclusivo para admin
    object Admin : HomeDestination("admin", Icons.Default.Settings, "Admin")

    companion object {
        val all: List<HomeDestination> = listOf(Productos, Despacho, Perfil)
        val adminOnly: List<HomeDestination> = listOf(Admin)
    }

}


@Composable
fun HomeNavHost(
    navController: NavHostController,
    auth: FirebaseAuth,
    onLogout: () -> Unit,
    rolUsuario: String?,
    modifier: Modifier = Modifier
) {
    val carritoViewModel: CarritoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = HomeDestination.Despacho.route,
        modifier = modifier
    ) {
        composable(HomeDestination.Despacho.route) {
            DespachoScreen(
                auth = auth,
                onLogout = onLogout,
                carritoViewModel = carritoViewModel
            )
        }
        composable(HomeDestination.Productos.route) {
            ProductosScreen(carritoViewModel = carritoViewModel)
        }
        composable(HomeDestination.Perfil.route) {
            PerfilScreen(
                auth = auth,
                onLogout = onLogout,
                rolUsuario = rolUsuario
            )
        }
        composable(HomeDestination.Admin.route) {
            if (rolUsuario == "admin") {
                PantallaAdmin(
                    auth = auth
                )
            } else {
                // Redirigir o mostrar acceso denegado
                Text("Acceso denegado")
            }
        }


    }
}