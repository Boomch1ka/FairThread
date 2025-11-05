package com.example.fairthread.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.R
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

    LaunchedEffect(authState) {
        authState?.onSuccess {
            navController.navigate("login") {
                popUpTo("register") { inclusive = true }
            }
        }?.onFailure {
            val message = it.message ?: context.getString(R.string.registration_failed)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
                text = stringResource(R.string.register),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.email)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.username)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(stringResource(R.string.confirm_password)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (password != confirmPassword) {
                        Toast.makeText(context, context.getString(R.string.passwords_do_not_match), Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isValidating = true
                    viewModel.validateEmailBeforeRegister(email.trim()) { isValid, error ->
                        if (!isValid) {
                            isValidating = false
                            Toast.makeText(context, error ?: context.getString(R.string.invalid_email), Toast.LENGTH_LONG).show()
                            return@validateEmailBeforeRegister
                        }

                        viewModel.register(email.trim(), password.trim())
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isValidating
            ) {
                Text(if (isValidating) stringResource(R.string.validating) else stringResource(R.string.register))
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = { navController.navigate("login") }) {
                Text(stringResource(R.string.already_have_account), color = WhiteText)
            }
        }
    }
}