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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.R
import com.example.fairthread.model.Product
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.viewmodel.CatalogueViewModel

@Composable
fun CatalogueScreen(navController: NavController, viewModel: CatalogueViewModel = viewModel()) {
    val categories by viewModel.categories.collectAsState()
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    FairThreadScaffold(navController, title = stringResource(R.string.catalogue)) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Text(stringResource(R.string.catalogue), style = MaterialTheme.typography.h4)

            Spacer(modifier = Modifier.height(16.dp))

            Text(stringResource(R.string.categories), style = MaterialTheme.typography.h6)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(vertical = 8.dp)) {
                items(categories) { category ->
                    Card(
                        modifier = Modifier.clickable { viewModel.selectCategory(category.name) },
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(category.name, style = MaterialTheme.typography.body1)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(stringResource(R.string.products), style = MaterialTheme.typography.h6)

            if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Text(
                    text = "${stringResource(R.string.error)}: $errorMessage",
                    color = MaterialTheme.colors.error
                )
            } else if (products.isEmpty()) {
                Text(stringResource(R.string.no_products_found))
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(products) { product: Product ->
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
