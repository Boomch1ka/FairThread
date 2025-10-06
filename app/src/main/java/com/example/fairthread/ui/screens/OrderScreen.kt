package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.ButtonColor
import com.example.fairthread.ui.theme.ButtonTextColor
import com.example.fairthread.viewmodel.OrderViewModel

@Composable
fun OrderScreen(navController: NavController, viewModel: OrderViewModel = viewModel()) {
    val orders by viewModel.orders.collectAsState()
    FairThreadBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "Your Orders",
                fontSize = 24.sp,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            LaunchedEffect(Unit) {
                viewModel.loadOrders()
            }

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(orders) { order ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = order, style = MaterialTheme.typography.body1)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { /* Handle individual order action */ },
                                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor)
                            ) {
                                Text("View", color = ButtonTextColor)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("payment") },
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pay", color = ButtonTextColor)
            }
        }
    }
}