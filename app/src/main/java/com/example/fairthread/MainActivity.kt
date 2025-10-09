package com.example.fairthread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.navigation.NavGraph
import com.example.fairthread.ui.theme.FairThreadTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        setContent {
            FairThreadTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController, uid = uid)
            }
        }
    }
}