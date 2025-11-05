package com.example.fairthread.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FairThreadScaffold(
    navController: NavController,
    title: String,
    showBack: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController)
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (showBack) {
                                navController.popBackStack()
                            } else {
                                scope.launch { drawerState.open() }
                            }
                        }) {
                            Icon(
                                imageVector = if (showBack) Icons.Default.ArrowBack else Icons.Default.Menu,
                                contentDescription = if (showBack) "Back" else "Menu"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}