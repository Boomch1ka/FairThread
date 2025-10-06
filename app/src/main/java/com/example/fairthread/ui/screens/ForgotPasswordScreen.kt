package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.ButtonColor
import com.example.fairthread.ui.theme.ButtonTextColor
import com.example.fairthread.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(navController: NavController, viewModel: ForgotPasswordViewModel = viewModel()) {
    OutlinedTextField(
        value = viewModel.email,
        onValueChange = { viewModel.email = it },
        label = { Text("Email") }
    )

    Button(onClick = { viewModel.sendResetLink() }) {
        Text("Send Reset Link")
    }

    Spacer(modifier = Modifier.height(12.dp))

    TextButton(onClick = { navController.navigate("login") }) {
        Text("Back to Login")
    }
}
