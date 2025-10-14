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
import com.example.fairthread.ui.preview.PreviewWrapper
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    var searchQuery by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Navigation", style = MaterialTheme.typography.h6, modifier = Modifier.padding(16.dp))
            Divider()
            DrawerItem("Catalogue") { navController.navigate("catalogue") }
            DrawerItem("Stores") { navController.navigate("stores") }
            DrawerItem("Cart") { navController.navigate("cart") }
            DrawerItem("Orders") { navController.navigate("orders") }
            DrawerItem("Settings") { navController.navigate("settings") }
        },
        topBar = {
            TopAppBar(
                title = { Text("FairThread") },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) {
        FairThreadBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search Goods / Merch / Services") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        // Trigger search logic
                    })
                )

                Button(onClick = { navController.navigate("catalogue") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Browse Catalogue")
                }

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

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    PreviewWrapper {
        HomeScreen(navController = rememberNavController())
    }
}