package com.example.fairthread.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fairthread.viewmodel.CheckoutViewModel

@Composable
fun CheckoutScreen(uid: String, cartItems: List<Map<String, Any>>, navController: NavHostController) {
    val viewModel: CheckoutViewModel = viewModel()
    val total by viewModel.total.collectAsState()

    LaunchedEffect(cartItems) {
        viewModel.calculateTotal(cartItems)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Checkout", style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))

        cartItems.forEach { item ->
            Text("${item["name"]} - R${item["price"]} x ${item["quantity"]}")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Total: R${"%.2f".format(total)}", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.placeOrder(uid, cartItems) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Place Order")
        }
    }
}