package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.*

@Composable
fun SplashScreen(navController: NavController) {
    FairThreadBackground {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("FairThread", color = WhiteText, fontSize = 32.sp)
            Spacer(Modifier.height(32.dp))
            Button(onClick = { navController.navigate("login") }, colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
                Text("Login", color = ButtonTextColor)
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { navController.navigate("register") }, colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
                Text("Register", color = ButtonTextColor)
            }
        }
    }
}