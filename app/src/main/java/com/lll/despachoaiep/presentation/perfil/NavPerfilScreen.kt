package com.lll.despachoaiep.presentation.perfil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.R
import com.lll.despachoaiep.ui.theme.BackgroundButton

@Composable
fun NavPerfilScreen(
    urlImg: String,
    contentDescription: String,
    nombrePerfil: String,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
    auth: FirebaseAuth,
    rolUsuario: String?
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = urlImg,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = nombrePerfil,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            if (rolUsuario != null) {
                ViewRolUser(
                    onClick = {},
                    rolUsuario = rolUsuario,
                )
            }

        }
        // Botón de cerrar sesión
        Button(
            onClick = {
                auth.signOut()
                onLogout()
            },
            colors = ButtonDefaults.buttonColors(containerColor = BackgroundButton),
            modifier = Modifier
                .size(56.dp) // ✅ tamaño cuadrado del botón
                .padding(8.dp),
            shape = CircleShape, // opcional si quieres que sea circular
            contentPadding = PaddingValues(0.dp) // elimina espacio interno
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Cerrar sesión",
                tint = Color.White, // o el color que prefieras
                modifier = Modifier.size(24.dp)
            )
        }
    }


}


@Composable
fun ViewRolUser(
    onClick: () -> Unit, rolUsuario: String
) {
    OutlinedButton(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp)
    ) {
        Text(text = rolUsuario, fontSize = 12.sp)
    }

}