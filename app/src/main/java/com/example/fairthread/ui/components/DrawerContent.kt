package com.example.fairthread.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fairthread.R

@Composable
fun DrawerContent(navController: NavController) {
    ModalDrawerSheet {
        Column(modifier = Modifier.padding(16.dp)) {
            NavigationDrawerItem(
                label = { Text(stringResource(R.string.menu_home)) },
                selected = false,
                onClick = { navController.navigate("home") }
            )
            NavigationDrawerItem(
                label = { Text(stringResource(R.string.catalogue)) },
                selected = false,
                onClick = { navController.navigate("catalogue") }
            )
            NavigationDrawerItem(
                label = { Text(stringResource(R.string.my_orders)) },
                selected = false,
                onClick = { navController.navigate("orders") }
            )
            NavigationDrawerItem(
                label = { Text(stringResource(R.string.action_settings)) },
                selected = false,
                onClick = { navController.navigate("settings") }
            )
        }
    }
}