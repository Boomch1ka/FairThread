package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.R
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.viewmodel.OrderViewModel

@Composable
fun OrderScreen(uid: String, navController: NavController, viewModel: OrderViewModel = viewModel()) {
    val orders by viewModel.orders.collectAsState()

    LaunchedEffect(uid) {
        viewModel.loadOrders(uid)
    }

    FairThreadScaffold(navController, title = stringResource(R.string.my_orders)) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            if (orders.isEmpty()) {
                Text(stringResource(R.string.no_orders_yet))
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(orders) { order ->
                        Card(modifier = Modifier.fillMaxWidth(), elevation = 4.dp) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("${stringResource(R.string.order_id)}: ${order.id}", style = MaterialTheme.typography.h6)
                                Spacer(modifier = Modifier.height(8.dp))
                                order.items.forEach {
                                    Text("- ${it.quantity} x ${it.name} @ R${it.price}", style = MaterialTheme.typography.body1)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Divider()
                                Spacer(modifier = Modifier.height(8.dp))
                                val total = order.items.sumOf { it.price * it.quantity }
                                Text("${stringResource(R.string.total)}: R${String.format("%.2f",total)}", style = MaterialTheme.typography.h6)
                            }
                        }
                    }
                }
            }
        }
    }
}