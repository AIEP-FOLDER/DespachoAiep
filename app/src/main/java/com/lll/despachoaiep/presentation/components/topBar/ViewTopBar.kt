package com.lll.despachoaiep.presentation.components.topBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.R
import com.lll.despachoaiep.ui.theme.Black
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


//---------------------
// CARRITO
//---------------------
class CarritoViewModel : ViewModel() {
    private val _contador = MutableStateFlow(0)
    val contador: StateFlow<Int> = _contador

    fun agregarProducto() {
        _contador.value += 1
    }

    fun resetearCarrito() {
        _contador.value = 0
    }
}

@Composable
fun CarritoIcon(viewModel: CarritoViewModel) {
    val contador by viewModel.contador.collectAsState()

    BadgedBox(
        badge = {
            if (contador > 0) {
                Badge { Text(contador.toString()) }
            }
        }
    ) {
        IconButton(onClick = { /* Navegar al carrito */ }) {
            Icon(
                painter = painterResource(id = R.drawable.carrito), // tu ícono
                contentDescription = "Carrito",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

//---------------------

//---------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContainer(
    nombreUsuario: String,
    auth: FirebaseAuth,
    onLogout: () -> Unit,
    carritoViewModel: CarritoViewModel

) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Black,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        title = {
            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.titleMedium
            )
        },
        actions = {
            CarritoIcon(viewModel = carritoViewModel)
        }
    )


    /*
    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
        modifier = Modifier.fillMaxWidth()
    ) {
        ViewNavbar(
            nombreUsuario = nombreUsuario,
            auth = auth,
            onLogout = onLogout
        )
    }
    */

}
