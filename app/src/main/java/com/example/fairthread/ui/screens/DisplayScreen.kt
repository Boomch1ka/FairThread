package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.R
import com.example.fairthread.model.Product
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.viewmodel.DisplayViewModel

@Composable
fun DisplayScreen(
    storeId: String,
    category: String,
    navController: NavController,
    viewModel: DisplayViewModel = viewModel()
) {
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    viewModel.loadProducts(storeId, category)

    FairThreadScaffold(navController, title = "${stringResource(id = R.string.category)}: ${category.replaceFirstChar { it.uppercase() }}") { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                errorMessage != null -> Text("${stringResource(id = R.string.error)}: $errorMessage", color = MaterialTheme.colors.error)
                products.isEmpty() -> Text(stringResource(id = R.string.no_products_found_in_category))
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = paddingValues
                    ) {
                        items(products) { product: Product ->
                            Card(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .clickable { navController.navigate("product/${product.id}") },
                                elevation = 4.dp
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(product.name, style = MaterialTheme.typography.h6)
                                    Text("R${product.price}", style = MaterialTheme.typography.body1)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
