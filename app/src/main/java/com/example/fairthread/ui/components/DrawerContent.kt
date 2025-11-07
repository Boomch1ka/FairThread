package com.example.fairthread.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fairthread.R

@Composable
fun DrawerContent(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        DrawerItem(navController = navController, route = "home", label = stringResource(R.string.menu_home))
        DrawerItem(navController = navController, route = "catalogue", label = stringResource(R.string.catalogue))
        DrawerItem(navController = navController, route = "orders", label = stringResource(R.string.my_orders))
        DrawerItem(navController = navController, route = "settings", label = stringResource(R.string.action_settings))
    }
}

@Composable
private fun DrawerItem(navController: NavController, route: String, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(route) }
            .padding(vertical = 12.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.h6)
    }
}
