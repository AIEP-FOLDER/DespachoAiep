package com.lll.despachoaiep.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun rememberRolUsuario(): String? {
    val usuarioActual = FirebaseAuth.getInstance().currentUser
    var rolUsuario by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(usuarioActual) {
        val uid = usuarioActual?.uid ?: return@LaunchedEffect
        val referencia = FirebaseDatabase.getInstance().getReference("usuarios/$uid/rol")

        referencia.get().addOnSuccessListener { snapshot ->
            rolUsuario = snapshot.value?.toString()
        }
    }

    return rolUsuario
}