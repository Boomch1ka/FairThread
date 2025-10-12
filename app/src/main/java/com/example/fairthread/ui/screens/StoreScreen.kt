package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
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
fun StoreScreen(
    storeId: String,
    navController: NavHostController,
    viewModel: StoreViewModel = viewModel()
) {
    val store by viewModel.store.collectAsState()
    val products by viewModel.products.collectAsState()

    LaunchedEffect(storeId) {
        viewModel.loadStore(storeId)
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        store?.let {
            Text(it.name, style = MaterialTheme.typography.h5)
            Text(it.description, style = MaterialTheme.typography.body2)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Products", style = MaterialTheme.typography.subtitle1)

        LazyColumn {
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
                        Text(product.name)
                        Text("R${product.price}")
                    }
                }
            }
        }
    }
}
