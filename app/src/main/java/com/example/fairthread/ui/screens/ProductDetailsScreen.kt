package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.model.Product
import com.example.fairthread.ui.preview.PreviewWrapper
import com.example.fairthread.ui.theme.FairThreadTheme
import com.example.fairthread.viewmodel.ProductViewModel

@Composable
fun ProductDetailsScreen(
    productId: String,
    navController: NavHostController,
    viewModel: ProductViewModel = viewModel()
) {
    val product by viewModel.product.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    product?.let {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text(it.name, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(8.dp))
            Text("R${it.price}", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.addToCart(it) }) {
                Text("Add to Cart")
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductDetailsScreen() {
    val mockViewModel = object : ProductViewModel() {
        init {
            _product.value = Product("1", "Denim Jacket", 499.99, "clothing")
        }
    }

    PreviewWrapper {
        ProductDetailsScreen(productId = "1", navController = rememberNavController(), viewModel = mockViewModel)
    }
}