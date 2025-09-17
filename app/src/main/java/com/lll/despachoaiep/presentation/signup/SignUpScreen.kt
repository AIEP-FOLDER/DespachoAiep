package com.lll.despachoaiep.presentation.signup

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.lll.despachoaiep.presentation.components.PasswordInputField
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(auth: FirebaseAuth, navController: NavHostController) {
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
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
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
                    unfocusedContainerColor = UnselectedField,
                    focusedContainerColor = SelectedField
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

                //auth.signInAnonymously()

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Registrado
                        Log.i("Matias", "Registro [OK]")
                        navController.navigate("logIn") {
                            popUpTo("signUp") { inclusive = true }
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
                                null // ya redirigimos
                            }

                            else -> {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Error al registrar: ${exception?.message}")
                                }
                                null
                            }
                        }


                        // error
                        Log.i("Matias", "Registro [KO]")


                    }
                }
            }) {
                Text("Sign Up")
            }
        }
    }
}

@Preview
@Composable
fun SignUpPreview() {
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
                    .padding(vertical = 36.dp)
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
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            )
        )
        Spacer(Modifier.height(48.dp))
        Text("Contraseña", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = password, onValueChange = { password = it }, modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            )
        )
        Spacer(Modifier.height(48.dp))
        Button(onClick = {

        }) {
            Text("Sign Up")
        }
    }
}