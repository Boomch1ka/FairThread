package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.ButtonColor
import com.example.fairthread.ui.theme.ButtonTextColor
import com.example.fairthread.viewmodel.DisplayViewModel

@Composable
fun DisplayScreen(navController: NavController, viewModel: DisplayViewModel = viewModel()) {
    val items by viewModel.items.collectAsState()

    FairThreadBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Display",
                fontSize = 24.sp,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            LaunchedEffect(Unit) {
                viewModel.loadDisplayItems()
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* navController.navigate("nikeDetails") */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Nike", color = ButtonTextColor)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* navController.navigate("shoesDetails") */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Shoes", color = ButtonTextColor)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* navController.navigate("info") */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Info", color = ButtonTextColor)
            }
        }
    }
}