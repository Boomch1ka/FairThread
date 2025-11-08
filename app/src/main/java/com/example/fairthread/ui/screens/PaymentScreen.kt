package com.example.fairthread.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.R
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.ui.theme.ButtonColor
import com.example.fairthread.ui.theme.ButtonTextColor
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
    var selectedPaymentMethod by remember { mutableStateOf("Visa") } // Default selection

    LaunchedEffect(uid) {
        cartViewModel.loadCart(uid)
    }

    FairThreadScaffold(navController, title = stringResource(id = R.string.payment_details)) { paddingValues ->
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
                    text = stringResource(id = R.string.choose_payment_method),
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.onSurface
                )

                Spacer(modifier = Modifier.height(24.dp))
                PaymentOption(
                    name = "Visa",
                    iconRes = R.drawable.ic_visa,
                    isSelected = selectedPaymentMethod == "Visa",
                    onClick = { selectedPaymentMethod = "Visa" }
                )

                Spacer(modifier = Modifier.height(16.dp))
                PaymentOption(
                    name = "MasterCard",
                    iconRes = R.drawable.ic_mastercard,
                    isSelected = selectedPaymentMethod == "MasterCard",
                    onClick = { selectedPaymentMethod = "MasterCard" }
                )

                Spacer(modifier = Modifier.height(16.dp))
                PaymentOption(
                    name = "PayPal",
                    iconRes = R.drawable.ic_paypal,
                    isSelected = selectedPaymentMethod == "PayPal",
                    onClick = { selectedPaymentMethod = "PayPal" }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (cartItems.isNotEmpty()) {
                            Log.d("Payment", "Payment processed for ${cartItems.size} items with $selectedPaymentMethod")
                            Toast.makeText(context, context.getString(R.string.payment_successful), Toast.LENGTH_SHORT)
                                .show()

                            orderViewModel.placeOrder(uid, cartItems)
                            cartViewModel.clearCart(uid)

                            navController.navigate("orders") {
                                popUpTo("cart") { inclusive = true }
                            }
                        } else {
                            Log.d("Payment", "Cart is empty — no payment processed")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.confirm_payment))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.return_home), color = ButtonTextColor)
                }
            }
        }
    }
}

@Composable
private fun PaymentOption(
    name: String,
    @DrawableRes iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.1f) else Color.Transparent
    val borderColor = if (isSelected) MaterialTheme.colors.primary else Color.Gray.copy(alpha = 0.5f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = name,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = name, style = MaterialTheme.typography.body1)
    }
}
