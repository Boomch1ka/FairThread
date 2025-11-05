package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fairthread.R
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

    FairThreadScaffold(navController, title = stringResource(R.string.my_orders)) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                Spacer(modifier = Modifier.height(16.dp))

                if (orders.isEmpty()) {
                    Text(stringResource(R.string.no_orders_yet))
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(orders) { order ->
                            val total = order.items.sumOf { it.price * it.quantity }

                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("${stringResource(R.string.order_id)}: ${order.id}", style = MaterialTheme.typography.titleMedium)
                                    Spacer(modifier = Modifier.height(8.dp))

                                    order.items.forEach {
                                        Text("- ${it.quantity} x ${it.name} @ R${it.price}", style = MaterialTheme.typography.bodyMedium)
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("${stringResource(R.string.total)}: R${"%.2f".format(total)}", style = MaterialTheme.typography.bodyLarge)
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