package com.example.fairthread.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.ui.preview.PreviewWrapper
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    //var searchQuery by remember { mutableStateOf("") }

    FairThreadScaffold(navController, title = "FairThread") {
        FairThreadBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                /*


                Button(onClick = { navController.navigate("catalogue") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Browse Catalogue")
                }*/

                Button(onClick = { navController.navigate("stores") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Explore Stores")
                }

                Button(onClick = { navController.navigate("cart") }, modifier = Modifier.fillMaxWidth()) {
                    Text("View Cart")
                }

                Button(onClick = { navController.navigate("orders") }, modifier = Modifier.fillMaxWidth()) {
                    Text("My Orders")
                }

                Button(onClick = { navController.navigate("settings") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Settings")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    PreviewWrapper {
        HomeScreen(navController = rememberNavController())
    }
}