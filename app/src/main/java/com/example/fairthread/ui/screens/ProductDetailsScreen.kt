package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fairthread.R
import com.example.fairthread.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductDetailsScreen(
    productId: String,
    navController: NavHostController,
    viewModel: ProductViewModel = viewModel()
) {
    val product by viewModel.product.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text(product?.name ?: stringResource(R.string.product_details)) })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                errorMessage != null -> Text(
                    stringResource(R.string.error) + ": " + errorMessage,
                    color = MaterialTheme.colors.error
                )

                product != null -> {
                    Column {
                        Text(product!!.name, style = MaterialTheme.typography.h5)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("R${product!!.price}", style = MaterialTheme.typography.body1)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            viewModel.addToCart(product!!)
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.added_to_cart))
                            }
                        }) {
                            Text(stringResource(R.string.add_to_cart))
                        }
                    }
                }

                else -> Text(stringResource(R.string.product_not_found), color = MaterialTheme.colors.error)
            }
        }
    }
}