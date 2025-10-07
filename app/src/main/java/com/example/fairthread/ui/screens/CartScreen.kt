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
fun CartScreen(uid: String, navController: NavController) {

    val viewModel: CartViewModel = viewModel()
    val cartItems by viewModel.cartItems.collectAsState()

    LaunchedEffect(uid) {
        viewModel.loadCart(uid)
    }


    Column(modifier = Modifier.padding(16.dp)) {
        Text("Your Cart", style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))

        cartItems.forEach { item ->
            Text("${item["name"]} - R${item["price"]} x ${item["quantity"]}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("payment") },
            colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Proceed to Payment", color = ButtonTextColor)
        }
    }
}