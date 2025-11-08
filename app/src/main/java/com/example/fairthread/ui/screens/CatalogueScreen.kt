package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
fun CatalogueScreen(navController: NavHostController, viewModel: CatalogueViewModel = viewModel()) {
    val categories by viewModel.categories.collectAsState()
    val products by viewModel.products.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCatalogue()
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(stringResource(id = R.string.catalogue), style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))

        Text(stringResource(id = R.string.categories), style = MaterialTheme.typography.subtitle1)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(categories) { category ->
                Card(
                    modifier = Modifier
                        .clickable { viewModel.filterProductsByCategory(category.id) },
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(category.name, style = MaterialTheme.typography.body1)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(stringResource(id = R.string.products), style = MaterialTheme.typography.subtitle1)

        when {
            errorMessage != null -> {
                Text("${stringResource(id = R.string.error)}: ${errorMessage}", color = MaterialTheme.colors.error)
            }
            products.isEmpty() -> {
                Text(stringResource(id = R.string.no_products_found))
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
