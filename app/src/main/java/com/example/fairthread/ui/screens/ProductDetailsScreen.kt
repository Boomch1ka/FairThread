package com.example.fairthread.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.viewmodel.CatalogueViewModel
import com.example.fairthread.viewmodel.ProductViewModel

@Composable
fun ProductDetailsScreen(productId: String, navController: NavController) {
    val viewModel: ProductViewModel = viewModel()
    val product by viewModel.product.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

}

