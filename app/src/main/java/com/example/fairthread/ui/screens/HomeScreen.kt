package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.R
import com.example.fairthread.ui.components.FairThreadBackground
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.ui.preview.PreviewWrapper

@Composable
fun HomeScreen(navController: NavController) {
    FairThreadScaffold(navController, title = stringResource(R.string.app_name)) {
        FairThreadBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Button(onClick = { navController.navigate("stores") }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.explore_stores))
                }

                Button(onClick = { navController.navigate("cart") }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.view_cart))
                }

                Button(onClick = { navController.navigate("orders") }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.my_orders))
                }

                Button(onClick = { navController.navigate("settings") }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.action_settings))
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