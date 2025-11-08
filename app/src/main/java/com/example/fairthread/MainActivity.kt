package com.example.fairthread

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.navigation.NavGraph
import com.example.fairthread.ui.theme.FairThreadTheme
import com.example.fairthread.viewmodel.AuthViewModel
import com.example.fairthread.viewmodel.LocaleViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Handle the permission result if needed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authViewModel: AuthViewModel by viewModels()

        setContent {
            val localeViewModel: LocaleViewModel = viewModel()
            val currentLocale by localeViewModel.locale.collectAsState()

            // Request notification permission
            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }

            key(currentLocale) {
                val layoutDirection = when (currentLocale.language) {
                    "ar", "he" -> LayoutDirection.Rtl // Example RTL languages
                    else -> LayoutDirection.Ltr
                }

                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    FairThreadTheme {
                        val navController = rememberNavController()
                        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                        NavGraph(
                            navController = navController, uid = uid, authViewModel = authViewModel
                        )
                    }
                }
            }
        }
    }
}
