package com.example.fairthread.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.ui.screens.*

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    uid: String
) {
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController = navController)
        }

        // 🔐 Auth Screens (optional)
        composable("login") {
            LoginScreen(navController = navController)
        }

        composable("register") {
            RegisterScreen(navController = navController)
        }

        composable("forgot") {
            ForgotPasswordScreen(navController = navController)
        }
        // 🏠 Home
        composable("home") {
            HomeScreen(navController = navController)
        }

        // 🏠 Catalogue
        composable("catalogue") {
            CatalogueScreen(navController = navController)
        }

        // 📦 Product Details
        composable("product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailsScreen(productId = productId, navController = navController)
        }

        // 🛒 Cart
        composable("cart") {
            CartScreen(uid = uid, navController = navController)
        }

        // 💳 Checkout
        composable("checkout") {
            // Replace with actual cartItems from ViewModel or NavArgs
            CheckoutScreen(uid = uid, cartItems = emptyList(), navController = navController)
        }

        // 📜 Orders
        composable("orders") {
            OrderScreen(uid = uid, navController = navController)
        }

        // ⚙️ Settings
        composable("settings") {
            SettingsScreen(uid = uid, navController = navController)
        }

        // 🏬 Stores List
        composable("stores") {
            StoresScreen(navController = navController)
        }

        // 🏪 Store Details
        composable("store/{storeId}") { backStackEntry ->
            val storeId = backStackEntry.arguments?.getString("storeId") ?: ""
            StoreScreen(storeId = storeId, navController = navController)
        }

        // 📬 Inbox (Gmail API)
        /*
        composable("inbox") {
            InboxScreen(uid = uid, navController = navController)
        }
         */



        // ✅ Optional: Order Confirmation
        /*
        composable("confirmation/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            OrderConfirmationScreen(orderId = orderId, navController = navController)
        }

         */
    }
}