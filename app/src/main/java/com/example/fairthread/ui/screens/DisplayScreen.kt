package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.ui.theme.ButtonColor
import com.example.fairthread.ui.theme.ButtonTextColor
import com.example.fairthread.viewmodel.DisplayViewModel

@Composable
fun DisplayScreen(storeId: String, category: String, navController: NavHostController, viewModel: DisplayViewModel = viewModel()) {
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts(storeId, category)
    }

    FairThreadScaffold(navController, title = "Category: ${category.capitalize()}") { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)) {

            when {
                isLoading -> CircularProgressIndicator()
                errorMessage != null -> Text("Error: $errorMessage", color = MaterialTheme.colors.error)
                products.isEmpty() -> Text("No products found in this category.")
                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(products) { product ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { navController.navigate("product/${product.id}") },
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
}