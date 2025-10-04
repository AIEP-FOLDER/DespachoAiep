package com.lll.despachoaiep.utils

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase
import com.lll.despachoaiep.presentation.signup.GoogleAuthClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
Funcion para registar usuario con rol // continue with google
 */

fun manejarLoginRegistroRealtimeDatabase(
    context: Context,
    googleClient: GoogleAuthClient,
    onGoogleLoginSuccess: () -> Unit
) {
    val scope = CoroutineScope(Dispatchers.Main)
    scope.launch {
        val success = googleClient.signIn()
        if (success) {
            val auth = FirebaseAuth.getInstance()
            val uid = auth.currentUser?.uid
            val email = auth.currentUser?.email

            val referencia = FirebaseDatabase.getInstance().getReference("usuarios/$uid")

            referencia.get().addOnSuccessListener { snapshot ->
                if (!snapshot.exists()) {
                    val datosUsuario = mapOf(
                        "correo" to email,
                        "rol" to "cliente"
                    )
                    referencia.setValue(datosUsuario).addOnCompleteListener {
                        Log.i("Matias", "Rol cliente asignado [Google]")
                        onGoogleLoginSuccess()
                    }
                } else {
                    onGoogleLoginSuccess()
                }
            }
        }
    }
}

fun manejarRegistroConCorreo(
    auth: FirebaseAuth,
    email: String,
    password: String,
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val uid = auth.currentUser?.uid
            val referencia = FirebaseDatabase.getInstance().getReference("usuarios/$uid")
            val datosUsuario = mapOf(
                "correo" to email,
                "rol" to "cliente"
            )

            referencia.setValue(datosUsuario).addOnCompleteListener {
                navController.navigate("login") {
                    popUpTo("signUp") { inclusive = true }
                }
            }
        } else {
            val exception = task.exception
            val errorMessage = when (exception) {
                is FirebaseAuthUserCollisionException -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("Este correo ya está registrado. Redirigiendo al login...")
                    }
                    navController.navigate("logIn") {
                        popUpTo("signUp") { inclusive = true }
                    }
                    null
                }

                else -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("Error al registrar: ${exception?.message}")
                    }
                    null
                }
            }
            Log.i("Matias", "Registro [KO]")
        }
    }
}