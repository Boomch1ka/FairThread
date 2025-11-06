package com.example.fairthread.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
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
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.R
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.viewmodel.AuthViewModel
import com.example.fairthread.viewmodel.SettingsViewModel
import java.util.Locale
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.ButtonColor
import com.example.fairthread.ui.theme.ButtonTextColor
import com.example.fairthread.ui.theme.FairThreadTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


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
    val scope = rememberCoroutineScope()

@Composable
fun SettingsScreen(navController: NavController) {
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
                    Toast.makeText(context, "Settings saved successfully", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes", color = ButtonTextColor)
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
    FairThreadScaffold(navController, title = stringResource(R.string.action_settings)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(R.string.select_language), modifier = Modifier.padding(bottom = 16.dp))
            languages.forEach { (languageCode, stringId) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = selectedLanguage == languageCode,
                        onClick = {
                            setSelectedLanguage(languageCode)
                            setLocale(context, languageCode)
                            // Force recomposition
                            (context as? Activity)?.recreate()
                        }
                    )
                    Text(stringResource(stringId), modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                scope.launch {
                    settingsViewModel.deleteUserAccount(uid) { success ->
                        if (success) {
                            Toast.makeText(
                                context, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                            navController.navigate("login")
                        } else {
                            Toast.makeText(context, "Failed to delete account", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }) {
                    Text("Delete My Account")
                }

fun setLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val appLocale = LocaleListCompat.create(locale)
    AppCompatDelegate.setApplicationLocales(appLocale)

    val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("language", languageCode).apply()
}

fun getCurrentLocale(context: Context): Locale {
    val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val languageCode = sharedPreferences.getString("language", "en")
    return Locale(languageCode!!)
}
