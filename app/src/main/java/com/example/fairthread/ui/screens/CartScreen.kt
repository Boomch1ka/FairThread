package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fairthread.R
import com.example.fairthread.model.CartItem
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.viewmodel.CartViewModel


@Composable
fun CartScreen(uid: String, navController: NavHostController, viewModel: CartViewModel = viewModel()) {
    val cartItems by viewModel.cartItems.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var itemToRemove by remember { mutableStateOf<CartItem?>(null) }

    LaunchedEffect(uid) {
        viewModel.loadCart(uid)
    }

    FairThreadScaffold(navController, title = stringResource(R.string.your_cart)) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                Spacer(modifier = Modifier.height(16.dp))

                if (cartItems.isEmpty()) {
                    Text(stringResource(R.string.your_cart_is_empty))
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(cartItems) { item: CartItem ->
                            var quantity by remember { mutableStateOf(item.quantity) }

                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(item.name, style = MaterialTheme.typography.h6)
                                    Text("R${item.price}", style = MaterialTheme.typography.body1)

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        OutlinedTextField(
                                            value = quantity.toString(),
                                            onValueChange = {
                                                quantity = it.toIntOrNull() ?: item.quantity
                                                viewModel.updateQuantity(uid, item.id, quantity)
                                            },
                                            label = { Text(stringResource(R.string.qty)) },
                                            modifier = Modifier.width(100.dp)
                                        )

                                        TextButton(onClick = {
                                            itemToRemove = item
                                            showDialog = true
                                        }) {
                                            Text(stringResource(R.string.remove))
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            navController.navigate("payment")
                        }, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.proceed_to_payment))
                    }
                }
            }

            if (showDialog && itemToRemove != null) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(stringResource(R.string.remove_item)) },
                    text = { Text(stringResource(R.string.remove_item_confirm)) },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.removeItem(uid, itemToRemove!!.id)
                            showDialog = false
                        }) {
                            Text(stringResource(R.string.yes))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                )
            }
        }
    }
}
