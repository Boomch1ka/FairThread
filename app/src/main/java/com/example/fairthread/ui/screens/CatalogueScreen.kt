package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fairthread.viewmodel.CatalogueViewModel

@Composable
fun CatalogueScreen(navController: NavHostController, viewModel: CatalogueViewModel = viewModel()) {
    val categories by viewModel.categories.collectAsState()
    val products by viewModel.products.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCatalogue()
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Catalogue", style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Categories", style = MaterialTheme.typography.subtitle1)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(categories) { category ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            // Optional: filter products by category
                        },
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(category.name, style = MaterialTheme.typography.body1)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Products", style = MaterialTheme.typography.subtitle1)
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