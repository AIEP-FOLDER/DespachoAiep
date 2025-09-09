package com.lll.despachoaiep

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lll.despachoaiep.presentation.home.HomeScreen
import com.lll.despachoaiep.presentation.initial.InitialScreen
import com.lll.despachoaiep.presentation.login.LoginScreen
import com.lll.despachoaiep.presentation.signup.SignUpScreen

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth
) {

    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("logIn") },
                navigateToSignUp = { navHostController.navigate("signUp") },
                onGoogleLoginSuccess = {
                    navHostController.navigate("home") {
                        popUpTo("initial") { inclusive = true }
                    }
                }

            )

        }
        composable("logIn") {
            LoginScreen(auth){ navHostController.navigate("PageHome") }
        }
        composable("signUp") {
            SignUpScreen(auth)
        }
        composable("home") {
            HomeScreen(
                auth = auth,
                onLogout = {
                    navHostController.navigate("initial") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }


    }
}