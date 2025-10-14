package com.example.fairthread.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.ButtonColor
import com.example.fairthread.ui.theme.ButtonTextColor

@Composable
fun PaymentScreen(navController: NavController) {
    FairThreadBackground {
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
                    //  PaymentOption("MasterCard", R.drawable.ic_mastercard)
                    Spacer(modifier = Modifier.height(16.dp))
                    //  PaymentOption("PayPal", R.drawable.ic_paypal)

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { navController.navigate("home") },
                        colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Return Home", color = ButtonTextColor)
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { navController.navigate("order") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Confirm Payment")
                    }

                    Button(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Return Home")
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentOption(label: String, iconRes: Int) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(label, style = MaterialTheme.typography.body1)
        }
    }
}