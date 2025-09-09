package com.lll.despachoaiep.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.R

@Composable
fun ViewNavbar(nombreUsuario: String, auth: FirebaseAuth, onLogout: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.weight(2f), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Bienvenido, $nombreUsuario",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Icon(
            painter = painterResource(id = R.drawable.logout),
            contentDescription = "Cerrar session",
            tint = Color.White,
            modifier = Modifier
                .padding(vertical = 36.dp)
                .size(25.dp)
                .clickable {
                    auth.signOut()
                    onLogout()
                }
                .align(Alignment.CenterVertically))

    }
}
