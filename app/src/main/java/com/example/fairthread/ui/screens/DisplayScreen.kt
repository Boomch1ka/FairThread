package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fairthread.R
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.viewmodel.DisplayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayScreen(storeId: String, category: String, navController: NavHostController, viewModel: DisplayViewModel = viewModel()) {
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts(storeId, category)
    }

    FairThreadScaffold(navController, title = "${stringResource(R.string.category)}: ${category.capitalize()}") { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)) {

            when {
                isLoading -> CircularProgressIndicator()
                errorMessage != null -> Text("${stringResource(R.string.error)}: $errorMessage", color = MaterialTheme.colorScheme.error)
                products.isEmpty() -> Text(stringResource(R.string.no_products_found_in_category))
                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(products) { product ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { navController.navigate("product/${product.id}") }
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(product.name, style = MaterialTheme.typography.bodyLarge)
                                    Text("R${product.price}", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}