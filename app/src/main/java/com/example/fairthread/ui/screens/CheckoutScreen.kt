package com.example.fairthread.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.R
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.viewmodel.CheckoutViewModel
import java.util.Locale

@Composable
fun CheckoutScreen(uid: String, navController: NavController, viewModel: CheckoutViewModel = viewModel()) {
    val cartItems by viewModel.cartItems.collectAsState()
    val total by viewModel.total.collectAsState()

    LaunchedEffect(uid) {
        viewModel.loadCart(uid)
    }

    FairThreadScaffold(navController, title = stringResource(R.string.checkout)) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Text(stringResource(R.string.checkout), style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(cartItems) { item ->
                    Text("${item.quantity} x ${item.name}", style = MaterialTheme.typography.h6)
                    Text("R${item.price}", style = MaterialTheme.typography.body1)
                    Divider()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("${stringResource(R.string.total)}: R${String.format(Locale.getDefault(), "%.2f", total)}", style = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { viewModel.placeOrder(uid) }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.place_order))
            }
        }
    }
}
