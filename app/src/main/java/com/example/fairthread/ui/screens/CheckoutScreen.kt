package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fairthread.viewmodel.CheckoutViewModel

@Composable
fun CheckoutScreen(uid: String, navController: NavHostController, viewModel: CheckoutViewModel = viewModel()) {
    val cartItems by viewModel.cartItems.collectAsState()
    val orderPlaced by viewModel.orderPlaced.collectAsState()

    LaunchedEffect(uid) {
        viewModel.loadCart(uid)
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Checkout", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(cartItems) { item ->
                Column {
                    Text(item.name, style = MaterialTheme.typography.subtitle1)
                    Text("Qty: ${item.quantity} | R${item.price}", style = MaterialTheme.typography.body2)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val total = cartItems.sumOf { it.price * it.quantity }
        Text("Total: R$total", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.placeOrder(uid) }, modifier = Modifier.fillMaxWidth()) {
            Text("Place Order")
        }

        if (orderPlaced) {
            LaunchedEffect(Unit) {
                navController.navigate("orders") {
                    popUpTo("checkout") { inclusive = true }
                }
            }
        }
    }
}