package com.example.fairthread.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.ButtonColor
import com.example.fairthread.ui.theme.ButtonTextColor
import com.example.fairthread.ui.theme.FairThreadTheme
import com.example.fairthread.viewmodel.AuthViewModel
import com.example.fairthread.viewmodel.SettingsViewModel
import com.google.firebase.auth.FirebaseAuth


@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val fakeNavController = rememberNavController()
    val fakeUid = "preview-user-id"

    FairThreadTheme {
        SettingsScreen(uid = fakeUid, navController = fakeNavController)
    }
}

@Composable
fun SettingsScreen(
    uid: String,
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val settings by settingsViewModel.settings.collectAsState()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var saveMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val settingsViewModel: SettingsViewModel = viewModel()

    LaunchedEffect(uid) {
        settingsViewModel.loadSettings(uid)
    }

    LaunchedEffect(settings) {
        settings?.let {
            username = it["username"]?.toString() ?: ""
            email = it["email"]?.toString() ?: ""
            password = it["password"]?.toString() ?: ""
        }
    }

    FairThreadBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Settings", fontSize = 24.sp, color = MaterialTheme.colors.onSurface)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
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

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    settingsViewModel.saveSettings(uid, username, email, password)
                    saveMessage = "Settings saved successfully"
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes", color = ButtonTextColor)
            }

            if (saveMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(saveMessage, color = MaterialTheme.colors.primary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("settings") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log Out")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                settingsViewModel.deleteUserAccount(uid) { success ->
                    if (success) {
                        Toast.makeText(context, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                        navController.navigate("login")
                    } else {
                        Toast.makeText(context, "Failed to delete account", Toast.LENGTH_SHORT).show()
                    }
                }
            }) {
                Text("Delete My Account")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = { navController.navigate("home") }) {
                Text("Back to Home")
            }
        }
    }
}

