package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fairthread.viewmodel.StoreViewModel


@Composable
fun StoreScreen(
    storeId: String,
    navController: NavHostController,
    viewModel: StoreViewModel = viewModel()
) {
    val store by viewModel.store.collectAsState()
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(storeId) {
        viewModel.loadStore(storeId)
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        store?.let {
            Text("🏪 ${it.name}", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(4.dp))
            Text(it.description, style = MaterialTheme.typography.body2)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Products", style = MaterialTheme.typography.subtitle1)

        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            errorMessage != null -> {
                Text("Error: $errorMessage", color = MaterialTheme.colors.error)
            }
            products.isEmpty() -> {
                Text("No products available in this store.")
            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(products) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("product/${product.id}")
                                },
                            elevation = 4.dp
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(product.name, style = MaterialTheme.typography.body1)
                                Text("R${product.price}", style = MaterialTheme.typography.body2)
                            }
                        }
                    }
                }
            }
        }
    }
}