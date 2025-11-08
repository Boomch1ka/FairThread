package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.R
import com.example.fairthread.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductDetailsScreen(
    productId: String,
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {
    val product by viewModel.product.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    viewModel.loadProduct(productId)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(title = { Text(product?.name ?: stringResource(id = R.string.product_details)) })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                errorMessage != null -> Text(
                    text = "${stringResource(id = R.string.error)}: $errorMessage",
                    color = MaterialTheme.colors.error
                )
                product != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(product!!.name, style = MaterialTheme.typography.h4)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("R${product!!.price}", style = MaterialTheme.typography.h5)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            viewModel.addToCart(product!!)
                            scope.launch {
                                snackbarHostState.showSnackbar(context.getString(R.string.add_to_cart))
                            }
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(stringResource(id = R.string.add_to_cart))
                        }
                    }
                }
                else -> Text(stringResource(id = R.string.product_not_found), color = MaterialTheme.colors.error)
            }
        }
    }
}
