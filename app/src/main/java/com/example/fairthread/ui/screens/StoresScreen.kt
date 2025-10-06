package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.ButtonColor
import com.example.fairthread.ui.theme.ButtonTextColor
import com.example.fairthread.viewmodel.StoresViewModel


@Composable
fun StoresScreen(navController: NavController, viewModel: StoresViewModel = viewModel()) {
    val stores by viewModel.stores.collectAsState()

    FairThreadBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "Stores",
                fontSize = 24.sp,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            LaunchedEffect(Unit) {
                viewModel.loadStores()
            }

            Spacer(modifier = Modifier.height(24.dp))

            val stores = listOf("Nike Store", "Adidas Outlet", "FairThread Merch", "Local Threads")

            stores.forEach { store ->
                Card(
                    elevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            // Navigate to store details screen with store name
                            navController.navigate("storeDetails?name=${store}")
                        }
                ) {
                    Text(
                        text = store,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("home") },
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Home", color = ButtonTextColor)
            }
        }
    }
}