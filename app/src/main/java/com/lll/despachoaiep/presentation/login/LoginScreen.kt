package com.lll.despachoaiep.presentation.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.lll.despachoaiep.R
import com.lll.despachoaiep.ui.theme.Black
import com.lll.despachoaiep.ui.theme.SelectedField
import com.lll.despachoaiep.ui.theme.UnselectedField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.lll.despachoaiep.presentation.components.PasswordInputField
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(auth: FirebaseAuth, navController: NavHostController, navigateToHome: () -> Unit) {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Black)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.arrowback),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(vertical = 53.dp)
                        .size(32.dp)
                        .clickable {
                            navController.popBackStack()
                        }

                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Text("Email", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = UnselectedField, focusedContainerColor = SelectedField
                )
            )
            Spacer(Modifier.height(48.dp))

            Text("Contraseña", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
            PasswordInputField(
                password = password,
                onPasswordChange = { password = it }
            )



            Spacer(Modifier.height(48.dp))
            Button(onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { loginTask ->
                        if (loginTask.isSuccessful) {
                            navigateToHome()
                            Log.i("Matias", "LOGIN [OK]")
                        } else {
                            val exception = loginTask.exception
                            val errorMessage = when {
                                exception is FirebaseAuthInvalidUserException &&
                                        exception.message?.contains("There is no user record") == true -> {
                                    "Este correo está vinculado a Google. Por favor inicia sesión con Google."
                                }

                                exception is FirebaseAuthInvalidCredentialsException -> {
                                    "Error. Intenta nuevamente."
                                }

                                else -> {
                                    "Error al iniciar sesión: ${exception?.message}"
                                }
                            }

                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = errorMessage,
                                    duration = SnackbarDuration.Long
                                )

                            }

                            Log.e("Matias", "LOGIN [KO]: ${exception?.message}")
                        }
                    }
            }) {
                Text("Login")
            }


        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Icon(
                painter = painterResource(id = R.drawable.arrowback),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .padding(vertical = 53.dp)
                    .size(32.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }



        Text("Email", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField, focusedContainerColor = SelectedField
            )
        )
        Spacer(Modifier.height(48.dp))
        Text("Contraseña", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        // añadir ocultar contraseña, con el ojo para poder visualizarla
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField, focusedContainerColor = SelectedField
            )
        )
        Spacer(Modifier.height(48.dp))
        Button(onClick = {}) {
            Text("Login")
        }
    }

}





