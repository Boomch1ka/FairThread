package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fairthread.viewmodel.StoreViewModel

@Composable
fun StoreScreen(storeId: String, navController: NavHostController, viewModel: StoreViewModel = viewModel()) {
    val store by viewModel.store.collectAsState()
    val products by viewModel.storeProducts.collectAsState()

    LaunchedEffect(storeId) {
        viewModel.loadStore(storeId)
        viewModel.loadProductsForStore(storeId)
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Store Details", style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))

        store?.let {
            val name = it["name"]?.toString() ?: "Unnamed Store"
            val description = it["description"]?.toString() ?: ""

            Text(name, style = MaterialTheme.typography.h6)
            Text(description, style = MaterialTheme.typography.body2)

            Spacer(modifier = Modifier.height(24.dp))
        }

        Text("Products", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(products) { product ->
                val name = product["name"]?.toString() ?: "Unnamed Product"
                val price = product["price"]?.toString() ?: "0.00"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(name, style = MaterialTheme.typography.subtitle1)
                        Text("R$price", style = MaterialTheme.typography.body2)
                    }
                }
            }
        }
    }
}