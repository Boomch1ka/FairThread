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
import com.example.fairthread.viewmodel.OrderViewModel

@Composable
fun OrderScreen(uid: String, navController: NavHostController, viewModel: OrderViewModel = viewModel()) {
    val orders by viewModel.orders.collectAsState()

    LaunchedEffect(uid) {
        viewModel.loadOrders(uid)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Orders", style = MaterialTheme.typography.h5) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        drawerContent = {
            Text("Navigation", style = MaterialTheme.typography.h6, modifier = Modifier.padding(16.dp))
            Divider()
            DrawerItem("Home") { navController.navigate("home") }
            DrawerItem("Stores") { navController.navigate("stores") }
            DrawerItem("Cart") { navController.navigate("cart") }
            DrawerItem("Orders") { navController.navigate("orders") }
            DrawerItem("Settings") { navController.navigate("settings") }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        )
        {
            Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(orders) { order ->
                        Card(modifier = Modifier.fillMaxWidth(), elevation = 4.dp) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                order.items.forEach {
                                    Text("${it.quantity} x ${it.name} - R${it.price}")
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Placed: ${order.timestamp}")
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}