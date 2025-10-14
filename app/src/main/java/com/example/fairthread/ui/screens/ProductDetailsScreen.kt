package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.model.Product
import com.example.fairthread.ui.preview.PreviewWrapper
import com.example.fairthread.ui.theme.FairThreadTheme
import com.example.fairthread.viewmodel.ProductViewModel

@Composable
fun ProductDetailsScreen(
    productId: String,
    navController: NavHostController,
    viewModel: ProductViewModel = viewModel()
) {
    val product by viewModel.product.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()


    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.name ?: "Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        drawerContent = {
            Text("FairThread", style = MaterialTheme.typography.h6, modifier = Modifier.padding(16.dp))
            Divider()
            DrawerItem("Home") { navController.navigate("home") }
            DrawerItem("Stores") { navController.navigate("stores") }
            DrawerItem("Cart") { navController.navigate("cart") }
            DrawerItem("Orders") { navController.navigate("orders") }
            DrawerItem("Settings") { navController.navigate("settings") }
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp)) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                errorMessage != null -> Text("Error: $errorMessage", color = MaterialTheme.colors.error)
                product != null -> {
                    Column {
                        Text(product!!.name, style = MaterialTheme.typography.h5)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("R${product!!.price}", style = MaterialTheme.typography.body1)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.addToCart(product!!) }) {
                            Text("Add to Cart")
                        }
                    }
                }
                else -> Text("Product not found", color = MaterialTheme.colors.error)
            }

        }
    }
}