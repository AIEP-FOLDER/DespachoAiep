package com.lll.despachoaiep.presentation.perfil


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.ui.theme.Black

@Composable
fun PerfilScreen(
    auth: FirebaseAuth,
) {
    val usuarioActual = FirebaseAuth.getInstance().currentUser
    //val nombreUsuario = usuarioActual?.displayName ?: "Usuario"
    val nombreUsuario = usuarioActual?.displayName?.takeIf { it.isNotBlank() } ?: "Usuario"

    val defaultAvatarUrl = "https://cdn-icons-png.flaticon.com/512/149/149071.png"
    val imgUsuario = usuarioActual?.photoUrl?.toString() ?: defaultAvatarUrl

    val scrollState = rememberScrollState()


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Black),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                NavPerfilScreen(imgUsuario, nombreUsuario, nombreUsuario)
            }



            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPerfilScreen() {
    val scrollState = rememberScrollState()

    val img = "https://i.pinimg.com/1200x/b0/55/60/b05560dbb2f4d4890e6e61e3f55d5e09.jpg"
    val name = "Matías Pérez"
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Black),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                // aqui tienen que ir todos mis composables
                NavPerfilScreen(img, name, name)

            }



            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}
