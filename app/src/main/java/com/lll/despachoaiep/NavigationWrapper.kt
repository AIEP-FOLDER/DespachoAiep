package com.lll.despachoaiep

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lll.despachoaiep.presentation.initial.InitialScreen
import com.lll.despachoaiep.presentation.login.LoginScreen
import com.lll.despachoaiep.presentation.signup.SignUpScreen

@Composable
fun NavigationWrapper(navHostController: NavHostController){

    NavHost(navController = navHostController, startDestination = "initial"){
        composable("initial"){
            InitialScreen()

        }
        composable("logIn"){
            LoginScreen()
        }
        composable("signUp"){
            SignUpScreen()
        }
    }
}