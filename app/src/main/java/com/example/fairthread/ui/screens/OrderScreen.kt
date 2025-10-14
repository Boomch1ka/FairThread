package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.viewmodel.OrderViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OrderScreen(uid: String, navController: NavHostController, viewModel: OrderViewModel = viewModel()) {
    val orders by viewModel.orders.collectAsState()

    LaunchedEffect(uid) {
        viewModel.loadOrders(uid)
    }

    FairThreadScaffold(navController, title = "My Orders") { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                Spacer(modifier = Modifier.height(16.dp))

                if (orders.isEmpty()) {
                    Text("You haven't placed any orders yet.")
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(orders) { order ->
                            val total = order.items.sumOf { it.price * it.quantity }
                            val formattedDate = formatTimestamp(order.timestamp)

                            Card(modifier = Modifier.fillMaxWidth(), elevation = 4.dp) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Order ID: ${order.id}", style = MaterialTheme.typography.subtitle2)
                                    Spacer(modifier = Modifier.height(8.dp))

                                    order.items.forEach {
                                        Text("- ${it.quantity} x ${it.name} @ R${it.price}", style = MaterialTheme.typography.body2)
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Total: R${"%.2f".format(total)}", style = MaterialTheme.typography.body1)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun formatTimestamp(raw: String): String {
    return try {
        val millis = raw.toLongOrNull() ?: return raw
        val date = Date(millis)
        val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        formatter.format(date)
    } catch (e: Exception) {
        raw
    }
}