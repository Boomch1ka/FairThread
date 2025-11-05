package com.example.fairthread.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.R
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.viewmodel.CartViewModel
import com.example.fairthread.viewmodel.OrderViewModel

@Composable
fun PaymentScreen(
    uid: String,
    navController: NavController,
    cartViewModel: CartViewModel = viewModel(),
    orderViewModel: OrderViewModel = viewModel()
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uid) {
        cartViewModel.loadCart(uid)
    }

    FairThreadScaffold(navController, title = stringResource(R.string.payment_details)) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.choose_payment_method),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(24.dp))

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (cartItems.isNotEmpty()) {
                            // Simulate payment success
                            Log.d("Payment", "Demo payment processed for ${cartItems.size} items")

                            Toast.makeText(context, context.getString(R.string.demo_payment_successful), Toast.LENGTH_SHORT).show()

                            // Place order and clear cart
                            orderViewModel.placeOrder(uid, cartItems)
                            cartViewModel.clearCart(uid)

                            // Navigate to orders screen
                            navController.navigate("orders") {
                                popUpTo("cart") { inclusive = true }
                            }
                        } else {
                            Log.d("Payment", "Cart is empty — no payment processed")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.confirm_payment))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.return_home))
                }
            }
        }
    }
}