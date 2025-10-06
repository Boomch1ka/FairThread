package com.example.fairthread.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.WhiteText

@Composable
fun home(navController: NavController) {
    FairThreadBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Specials", color = WhiteText, fontSize = 24.sp)
            Spacer(Modifier.height(16.dp))
            listOf("10%", "33%", "50%", "60%").forEach { label ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(WhiteText.copy(alpha = 0.2f))
                        .padding(8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(label, color = WhiteText)
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}