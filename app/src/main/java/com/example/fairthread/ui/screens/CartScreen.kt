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
                        Text("Item: ${item.name}")
                        Text("Price: R${item.price}")
                        Text("Quantity: ${item.quantity}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = { viewModel.removeItem(item.id) },
                                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor)
                            ) {
                                Text("Remove", color = ButtonTextColor)
                            }

                            Button(
                                onClick = {
                                    if (item.quantity > 1) {
                                        viewModel.updateQuantity(item.id, item.quantity - 1)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor)
                            ) {
                                Text("-", color = ButtonTextColor)
                            }

                            Text(
                                "Qty: ${item.quantity}",
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            Button(
                                onClick = {
                                    viewModel.updateQuantity(item.id, item.quantity + 1)
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor)
                            ) {
                                Text("+", color = ButtonTextColor)
                            }
                        }
                    }

                    val total = cartItems.sumOf { it.price * it.quantity }

                    Text("Total: R${"%.2f".format(total)}", fontSize = 20.sp)

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
        }
    }
}
