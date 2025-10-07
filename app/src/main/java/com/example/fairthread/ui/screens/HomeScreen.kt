package com.example.fairthread.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.theme.WhiteText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home

@Composable
fun HomeScreen(navController: NavController) {
    FairThreadBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "App Icon",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colors.onSurface
                )


                var searchQuery by remember { mutableStateOf("") }

                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search Goods / Merch / Services") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            // You can trigger search logic here
                        }
                    )
                )

                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "View Icon",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colors.onSurface
                )

            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("catalogue") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Browse Catalogue")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("cart") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Cart")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("stores") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Explore Stores")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("settings") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Settings")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Specials Section
            Text(
                text = "Specials",
                fontSize = 20.sp,
                color = WhiteText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                listOf("50%", "25%", "50%", "60%").forEach { label ->
                    Button(
                        onClick = { /* Navigate or show offer */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(label)
                    }
                }
            }
        }
    }
}