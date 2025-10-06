package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.ButtonColor
import com.example.fairthread.ui.theme.ButtonTextColor
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fairthread.viewmodel.CartViewModel

@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel = viewModel()) {
    val cartItems by viewModel.cartItems.collectAsState()

    FairThreadBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Cart", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = 4.dp
                    ) {
                        Text(item, modifier = Modifier.padding(16.dp))
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
