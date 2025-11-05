package com.example.fairthread.ui.screens

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import com.example.fairthread.ui.components.FairThreadScaffold
import java.util.Locale

@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val languages = listOf("English", "Afrikaans", "Xhosa")
    val currentLocale = getCurrentLocale(context)
    val (selectedLanguage, setSelectedLanguage) = remember { mutableStateOf(currentLocale.language) }

    FairThreadScaffold(navController, title = "Settings") {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Select Language", modifier = Modifier.padding(bottom = 16.dp))
            languages.forEach { language ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = selectedLanguage == language.substring(0, 2).toLowerCase(Locale.ROOT),
                        onClick = { 
                            val newLanguageCode = language.substring(0, 2).toLowerCase(Locale.ROOT)
                            setSelectedLanguage(newLanguageCode)
                            setLocale(context, newLanguageCode)
                        }
                    )
                    Text(language, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
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
