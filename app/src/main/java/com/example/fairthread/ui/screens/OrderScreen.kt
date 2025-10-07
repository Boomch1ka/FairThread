package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fairthread.viewmodel.OrderViewModel

@Composable
fun OrderScreen(uid: String, navController: NavHostController) {
    val viewModel: OrderViewModel = viewModel()
    val orders by viewModel.orders.collectAsState()

    LaunchedEffect(uid) {
        viewModel.loadOrders(uid)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Your Orders", style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))

        if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No orders found.")
            }
        } else {
            LazyColumn {
                items(orders) { order ->
                    val orderId = order["orderId"] ?: "N/A"
                    val date = order["orderedAt"]?.toString() ?: "Unknown"
                    val total = order["total"] ?: 0.0
                    val status = order["status"] ?: "pending"

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Order ID: $orderId", style = MaterialTheme.typography.subtitle1)
                            Text("Date: $date")
                            Text("Total: R${"%.2f".format(total)}")
                            Text("Status: ${status.toString().capitalize()}")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}