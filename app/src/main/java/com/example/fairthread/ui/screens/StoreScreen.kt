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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.R
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.viewmodel.StoreViewModel

@Composable
fun StoreScreen(
    storeId: String,
    navController: NavController,
    viewModel: StoreViewModel = viewModel()
) {
    val store by viewModel.store.collectAsState()
    val categories by viewModel.categories.collectAsState()

    val groupedProducts by viewModel.groupedProducts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(storeId) {
        if (storeId.isNotBlank()) {
            viewModel.loadStore(storeId)
            viewModel.loadStoreCategories(storeId)
        }
    }

    FairThreadScaffold(navController, title = store?.name ?: "Store") { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                errorMessage != null -> {
                    Text("${stringResource(R.string.error)}: $errorMessage", color = MaterialTheme.colors.error)
                }else -> {
                    Text(stringResource(R.string.categories), style = MaterialTheme.typography.h5)

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(categories) { category ->
                            Card(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .clickable { navController.navigate("store/$storeId/category/$category") },
                                elevation = 4.dp
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(category.capitalize(), style = MaterialTheme.typography.h6)
                                }
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
fun PreviewStoreScreen() {
    val mockViewModel = object : StoreViewModel() {
        init {
            _store.value = Store("threadco", "Thread & Co", "Local fashion boutique")
            _groupedProducts.value = mapOf(
                "clothing" to listOf(Product("1", "Denim Jacket", 499.99, "clothing")),
                "accessories" to listOf(Product("2", "Leather Belt", 199.99, "accessories"))
            )
        }
    }

    PreviewWrapper {
        StoreScreen(storeId = "threadco", navController = rememberNavController(), viewModel = mockViewModel)
    }
}
