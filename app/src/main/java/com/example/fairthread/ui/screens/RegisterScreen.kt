package com.example.fairthread.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.WhiteText
import com.example.fairthread.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isValidating by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.testManualEmailValidation()
    }

     LaunchedEffect(authState) {
        authState?.onSuccess {
            navController.navigate("login") {
                popUpTo("register") { inclusive = true }
            }
        }?.onFailure {
            Toast.makeText(context, it.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
        }
    }


    FairThreadBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isValidating = true
                    viewModel.validateEmailBeforeRegister(email.trim()) { isValid, error ->
                        if (!isValid) {
                            isValidating = false
                            Toast.makeText(context, error ?: "Invalid email", Toast.LENGTH_LONG).show()
                            return@validateEmailBeforeRegister
                        }

                        viewModel.register(email.trim(), password.trim())
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isValidating
            ) {
                Text(if (isValidating) "Validating..." else "Register")
            }

            Button(
                onClick = { viewModel.testManualEmailValidation() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Run Email API Test")
            }


            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = { navController.navigate("login") }) {
                Text("Already have an account? Login", color = WhiteText)
            }
        }
    }
}