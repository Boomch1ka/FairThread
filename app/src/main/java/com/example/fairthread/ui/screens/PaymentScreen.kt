package com.example.fairthread.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.FirebasePushNoti
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.ui.theme.ButtonColor
import com.example.fairthread.ui.theme.ButtonTextColor
import androidx.compose.runtime.*
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

    FairThreadScaffold(navController, title = "Payment Details") { paddingValues ->
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
                    text = "Choose Payment Method",
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.onSurface
                )

                Spacer(modifier = Modifier.height(24.dp))
                // PaymentOption("Visa", R.drawable.ic_visa)
                Spacer(modifier = Modifier.height(16.dp))
                // PaymentOption("MasterCard", R.drawable.ic_mastercard)
                Spacer(modifier = Modifier.height(16.dp))
                // PaymentOption("PayPal", R.drawable.ic_paypal)

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (cartItems.isNotEmpty()) {
                            Log.d("Payment", "Demo payment processed for ${cartItems.size} items")
                            Toast.makeText(context, "Demo payment successful!", Toast.LENGTH_SHORT)
                                .show()


                            orderViewModel.placeOrder(uid, cartItems)
                            cartViewModel.clearCart(uid)


                            FirebasePushNoti.createNotificationChannel(context)
                            FirebasePushNoti.sendOrderAcceptedNotification(context)


                            navController.navigate("orders") {
                                popUpTo("cart") { inclusive = true }
                            }
                        } else {
                            Log.d("Payment", "Cart is empty — no payment processed")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirm Payment")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Return Home", color = ButtonTextColor)
                }
            }
        }
    }
}

