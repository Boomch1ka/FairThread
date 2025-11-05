package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.R
import com.example.fairthread.model.Category
import com.example.fairthread.model.Product
import com.example.fairthread.ui.preview.PreviewWrapper
import com.example.fairthread.viewmodel.CatalogueViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogueScreen(navController: NavHostController, viewModel: CatalogueViewModel = viewModel()) {
    val categories by viewModel.categories.collectAsState()
    val products by viewModel.products.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCatalogue()
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(stringResource(R.string.catalogue), style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Text(stringResource(R.string.categories), style = MaterialTheme.typography.titleMedium)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(categories) { category ->
                Card(
                    modifier = Modifier
                        .clickable { viewModel.filterProductsByCategory(category.id) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(category.name, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(stringResource(R.string.products), style = MaterialTheme.typography.titleMedium)

        when {
            errorMessage != null -> {
                Text(
                    stringResource(R.string.error) + ": " + errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
            products.isEmpty() -> {
                Text(stringResource(R.string.no_products_found))
            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(products) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("product/${product.id}")
                                }
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

@Preview(showBackground = true)
@Composable
fun PreviewCatalogueScreen() {
    val mockViewModel = object : CatalogueViewModel() {
        init {
            _categories.value = listOf(
                Category("clothing", "Clothing"),
                Category("accessories", "Accessories")
            )
            _products.value = listOf(
                Product("1", "Denim Jacket", 499.99, "clothing"),
                Product("2", "Leather Belt", 199.99, "accessories")
            )
        }
    }

    PreviewWrapper {
        CatalogueScreen(navController = rememberNavController(), viewModel = mockViewModel)
    }
}