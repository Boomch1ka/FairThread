package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fairthread.ui.components.*
import com.example.fairthread.ui.theme.*

@Composable
fun login(navController: NavController) {
    FairThreadBackground {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login", color = WhiteText, fontSize = 24.sp)
            Spacer(Modifier.height(16.dp))
            FairTextField("Username")
            FairTextField("Password", isPassword = true)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { navController.navigate("home") }, colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)) {
                Text("LOGIN", color = ButtonTextColor)
            }
            TextButton(onClick = { navController.navigate("forgot") }) {
                Text("Forgot Password?", color = WhiteText)
            }
        }
    }
}