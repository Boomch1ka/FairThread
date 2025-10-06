package com.example.fairthread.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fairthread.ui.screens.*

@Composable
fun navGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("cart") { CartScreen(navController) }
        composable("catalogue") { CatalogueScreen(navController) }
        composable("payment") { PaymentScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("stores") { StoresScreen(navController) }

        composable("forgot") { ForgotPasswordScreen(navController) }

    }
}