package com.example.fairthread.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fairthread.ui.screens.*
import com.example.fairthread.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(
    navController: NavHostController,
    uid: String,
    authViewModel: AuthViewModel
) {
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: uid

    NavHost(navController = navController, startDestination = "splash") {

        // 🚀 Splash Screen
        composable("splash") {
            SplashScreen(navController = navController)
        }

        // 🔐 Authentication
        composable("login") {
            LoginScreen(navController = navController, viewModel = authViewModel)
        }

        composable("register") {
            RegisterScreen(navController = navController, viewModel = authViewModel)
        }

        composable("forgot") {
            ForgotPasswordScreen(navController = navController)
        }

        // 🏠 Home
        composable("home") {
            HomeScreen(navController = navController, uid = currentUserUid)
        }

        // 🛍️ Catalogue
        composable("catalogue") {
            CatalogueScreen(navController = navController)
        }

        // 🛒 Cart
        composable("cart") {
            CartScreen(uid = currentUserUid, navController = navController)
        }

        // 💳 Checkout
        composable("checkout") {
            CheckoutScreen(uid = currentUserUid, navController = navController)
        }

        // 📜 Orders
        composable("orders") {
            OrderScreen(uid = currentUserUid, navController = navController)
        }

        // ⚙️ Settings
        composable("settings") {
            SettingsScreen(uid = currentUserUid, navController = navController)
        }

        // 🏬 Stores List
        composable("stores") {
            StoresScreen(navController = navController)
        }

        // 🏪 Store Landing Page (shows categories)
        composable("store/{storeId}") { backStackEntry ->
            val storeId = backStackEntry.arguments?.getString("storeId") ?: ""
            StoreScreen(storeId = storeId, navController = navController)
        }

        // 🏪 Store Details
        composable("store/{storeId}/category/{category}") { backStackEntry ->
            val storeId = backStackEntry.arguments?.getString("storeId") ?: ""
            val category = backStackEntry.arguments?.getString("category") ?: ""
            DisplayScreen(storeId = storeId, category = category, navController = navController)
        }

        // 📦 Product Details
        composable("product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailsScreen(productId = productId, navController = navController)
        }

        composable("payment") {
            PaymentScreen(navController = navController, uid = currentUserUid)
        }


        // 📬 Inbox (Gmail API) — Optional
        /*
        composable("inbox") {
            InboxScreen(uid = uid, navController = navController)
        }
        */

        // ✅ Order Confirmation — Optional
        /*
        composable("confirmation/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            OrderConfirmationScreen(orderId = orderId, navController = navController)
        }
        */
    }
}
