package com.example.fairthread.ui.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.R
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.ui.theme.FairThreadTheme
import com.example.fairthread.viewmodel.AuthViewModel
import com.example.fairthread.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import java.util.Locale

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
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val languages = mapOf(
        "en" to R.string.english,
        "af" to R.string.afrikaans,
        "xh" to R.string.xhosa
    )
    val currentLocale = getCurrentLocale(context)
    val (selectedLanguage, setSelectedLanguage) = remember { mutableStateOf(currentLocale.language) }

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

    FairThreadScaffold(
        navController = navController,
        title = stringResource(R.string.action_settings)
    ) { paddingValues ->
        FairThreadBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Settings", fontSize = 24.sp, color = MaterialTheme.colors.onSurface)

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        settingsViewModel.saveSettings(uid, username, email, password)
                        Toast.makeText(context, "Settings saved successfully", Toast.LENGTH_SHORT)
                            .show()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes", color = MaterialTheme.colors.onPrimary)
                }

                // Language Selection
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    stringResource(R.string.select_language),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                languages.forEach { (languageCode, stringId) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedLanguage == languageCode,
                            onClick = {
                                setSelectedLanguage(languageCode)
                                setLocale(context, languageCode)
                                (context as? Activity)?.recreate()
                            }
                        )
                        Text(
                            stringResource(stringId),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
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

                Button(onClick = {
                    scope.launch {
                        settingsViewModel.deleteUserAccount(uid) { success ->
                            if (success) {
                                Toast.makeText(
                                    context, "Account deleted successfully", Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate("login") {
                                    popUpTo("settings") { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, "Failed to delete account", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }) {
                    Text("Delete My Account")
                }
            }
        }
    }
}

private fun setLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val appLocale = LocaleListCompat.create(locale)
    AppCompatDelegate.setApplicationLocales(appLocale)

    val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("language", languageCode).apply()
}

private fun getCurrentLocale(context: Context): Locale {
    val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val languageCode = sharedPreferences.getString("language", "en")
    return Locale(languageCode!!)
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    FairThreadTheme {
        SettingsScreen(uid = "preview-user-id", navController = rememberNavController())
    }
}
