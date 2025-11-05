package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.R
import com.example.fairthread.model.Store
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.ui.preview.PreviewWrapper
import com.example.fairthread.viewmodel.StoresViewModel

@Composable
fun StoresScreen(navController: NavHostController, viewModel: StoresViewModel = viewModel()) {
    val stores by viewModel.stores.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadStores()
    }

    FairThreadScaffold(navController, title = stringResource(R.string.stores)) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                when {
                    isLoading -> {
                        CircularProgressIndicator()
                    }

                    errorMessage != null -> {
                        Text("${stringResource(R.string.error)}: $errorMessage", color = MaterialTheme.colors.error)
                    }

                    stores.isEmpty() -> {
                        Text(stringResource(R.string.no_stores_available))
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
                                        Text(
                                            store.description,
                                            style = MaterialTheme.typography.body2
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}