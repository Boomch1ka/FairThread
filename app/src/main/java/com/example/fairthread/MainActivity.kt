package com.example.fairthread

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.navigation.NavGraph
import com.example.fairthread.ui.theme.FairThreadTheme
import com.example.fairthread.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the app's locale based on the saved preference
        val sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("language", "en") // Default to English
        val locale = Locale(languageCode!!)
        val appLocale = LocaleListCompat.create(locale)
        AppCompatDelegate.setApplicationLocales(appLocale)

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Initialize AuthViewModel
        val authViewModel: AuthViewModel by viewModels()

        setContent {
            FairThreadTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController, uid = uid, authViewModel = authViewModel
                )
            }
        }
    }
}