package com.lll.despachoaiep.presentation.components.topBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.presentation.components.ViewNavbar


@Composable
fun TopBarContainer(
    nombreUsuario: String,
    auth: FirebaseAuth,
    onLogout: () -> Unit
) {
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
}
