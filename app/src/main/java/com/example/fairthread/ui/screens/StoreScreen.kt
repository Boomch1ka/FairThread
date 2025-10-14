package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.model.Product
import com.example.fairthread.model.Store
import com.example.fairthread.ui.preview.PreviewWrapper
import com.example.fairthread.ui.theme.FairThreadTheme
import com.example.fairthread.viewmodel.StoreViewModel


@Composable
fun StoreScreen(storeId: String, navController: NavHostController, viewModel: StoreViewModel = viewModel()) {
    val store by viewModel.store.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val groupedProducts by viewModel.groupedProducts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(storeId) {
        viewModel.loadStore(storeId)
        viewModel.loadStoreCategories(storeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(store?.name ?: "Store") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                errorMessage != null -> {
                    Text("Error: $errorMessage", color = MaterialTheme.colors.error)
                }
                else -> {
                    Text("Categories", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(categories) { category ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate("store/${storeId}/category/${category}")
                                    },
                                elevation = 4.dp
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(category.capitalize(), style = MaterialTheme.typography.body1)
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