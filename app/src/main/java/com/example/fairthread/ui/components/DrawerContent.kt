package com.example.fairthread.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DrawerContent(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("FairThread", style = MaterialTheme.typography.h6, modifier = Modifier.padding(16.dp))
        Divider()
        DrawerItem("Home") { navController.navigate("home") }
        DrawerItem("Stores") { navController.navigate("stores") }
        DrawerItem("Cart") { navController.navigate("cart") }
        DrawerItem("Orders") { navController.navigate("orders") }
        DrawerItem("Settings") { navController.navigate("settings") }
    }
}

@Composable
fun DrawerItem(label: String, onClick: () -> Unit) {
    Text(
        text = label,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        style = MaterialTheme.typography.body1
    )
}