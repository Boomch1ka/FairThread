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
import com.example.fairthread.viewmodel.StoresViewModel

@Composable
fun StoresScreen(navController: NavHostController, viewModel: StoresViewModel = viewModel()) {
    val stores by viewModel.stores.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadStores()
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("🏬 Explore Stores", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            errorMessage != null -> {
                Text("Error: $errorMessage", color = MaterialTheme.colors.error)
            }
            stores.isEmpty() -> {
                Text("No stores available.")
            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(stores) { store ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navController.navigate("store/${store.id}")
                                },
                                    elevation = 4.dp
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(store.name, style = MaterialTheme.typography.subtitle1)
                                Text(store.description, style = MaterialTheme.typography.body2)
                            }
                        }
                    }
                }
            }
        }
    }
}