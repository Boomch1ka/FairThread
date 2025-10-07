package com.example.fairthread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.navigation.NavGraph
import com.example.fairthread.ui.theme.FairThreadTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FairThreadTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}