package com.lll.despachoaiep.presentation.components.topBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.R
import com.lll.despachoaiep.presentation.components.ViewNavbar
import com.lll.despachoaiep.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContainer(
    nombreUsuario: String,
    auth: FirebaseAuth,
    onLogout: () -> Unit
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
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Cerrar sesión",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        auth.signOut()
                        onLogout()
                    }
            )
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
