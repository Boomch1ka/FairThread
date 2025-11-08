package com.example.fairthread.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.R
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.FairThreadTheme
import com.example.fairthread.viewmodel.AuthViewModel
import com.example.fairthread.viewmodel.LocaleViewModel
import com.example.fairthread.viewmodel.SettingsViewModel

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
    authViewModel: AuthViewModel = viewModel(),
    localeViewModel: LocaleViewModel = viewModel()
) {
    val context = LocalContext.current
    val languages = mapOf(
        "en" to stringResource(id = R.string.english),
        "af" to stringResource(id = R.string.afrikaans),
        "xh" to stringResource(id = R.string.xhosa)
    )
    val currentLocale by localeViewModel.locale.collectAsState()
    var pendingLanguage by remember { mutableStateOf(currentLocale.language) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text(stringResource(id = R.string.confirm_language_change)) },
            text = { Text("${stringResource(id = R.string.change_language_to)} ${languages[pendingLanguage]}?") },
            confirmButton = {
                TextButton(onClick = {
                    localeViewModel.setLocale(pendingLanguage)
                    showLanguageDialog = false
                }) {
                    Text(stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }

    FairThreadBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(id = R.string.settings), fontSize = 24.sp, color = MaterialTheme.colors.onSurface)
            Spacer(modifier = Modifier.height(24.dp))

            // Language Selection
            Text(
                stringResource(R.string.select_language),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(8.dp))

            languages.forEach { (languageCode, languageName) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (languageCode != currentLocale.language) {
                                pendingLanguage = languageCode
                                showLanguageDialog = true
                            }
                        }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = currentLocale.language == languageCode,
                        onClick = {
                            if (languageCode != currentLocale.language) {
                                pendingLanguage = languageCode
                                showLanguageDialog = true
                            }
                        }
                    )
                    Text(text = languageName, modifier = Modifier.padding(start = 8.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.log_out))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                settingsViewModel.deleteUserAccount(uid) { success ->
                    if (success) {
                        Toast.makeText(context, context.getString(R.string.account_deleted_successfully), Toast.LENGTH_SHORT).show()
                        navController.navigate("login"){
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, context.getString(R.string.failed_to_delete_account), Toast.LENGTH_SHORT).show()
                    }
                }
            }) {
                Text(stringResource(id = R.string.delete_my_account))
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = { navController.navigate("home") }) {
                Text(stringResource(id = R.string.back_to_home))
            }
        }
    }
}
