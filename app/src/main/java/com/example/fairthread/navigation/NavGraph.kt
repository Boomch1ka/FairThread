package com.example.fairthread.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fairthread.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("cart") { CartScreen(navController) }
        composable("catalogue") { CatalogueScreen(navController) }
        composable("display") { DisplayScreen(navController) }
        composable("storeDetails?name={name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            StoreDetailsScreen(navController, storeName = name ?: "")
        }
        composable("order") { OrderScreen(navController) }
        composable("payment") { PaymentScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("stores") { StoresScreen(navController) }

        composable("forgot") { ForgotPasswordScreen(navController) }
    }
}