package com.example.fairthread.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun FairThreadScaffold(
    navController: NavController,
    title: String,
    showBack: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (showBack) {
                            navController.popBackStack()
                        } else {
                            coroutineScope.launch { scaffoldState.drawerState.open() }
                        }
                    }) {
                        Icon(
                            imageVector = if (showBack) Icons.Default.ArrowBack else Icons.Default.Menu,
                            contentDescription = if (showBack) "Back" else "Menu"
                        )
                    }
                }
            )
        },
        drawerContent = {
            DrawerContent(navController)
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}