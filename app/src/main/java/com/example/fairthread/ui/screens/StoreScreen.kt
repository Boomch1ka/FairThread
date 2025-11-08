package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.R
import com.example.fairthread.model.Product
import com.example.fairthread.model.Store
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.ui.preview.PreviewWrapper
import com.example.fairthread.viewmodel.StoreViewModel

@Composable
fun StoreScreen(storeId: String, navController: NavHostController, viewModel: StoreViewModel = viewModel()) {
    val store by viewModel.store.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    viewModel.loadStoreAndCategories(storeId)

    FairThreadScaffold(navController, title = store?.name ?: stringResource(id = R.string.store)) { paddingValues ->
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
                    Text("${stringResource(id = R.string.error)}: $errorMessage", color = MaterialTheme.colors.error)
                }
                else -> {
                    Text(stringResource(id = R.string.categories), style = MaterialTheme.typography.h6)
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
                                    Text(category.replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.body1)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}