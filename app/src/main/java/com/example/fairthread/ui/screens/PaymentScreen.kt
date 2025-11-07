package com.example.fairthread.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fairthread.R
import com.example.fairthread.ui.components.FairThreadScaffold
import com.example.fairthread.viewmodel.PaymentViewModel

@Composable
fun PaymentScreen(navController: NavController, uid: String, viewModel: PaymentViewModel = viewModel()) {
    val paymentStatus by viewModel.paymentStatus.collectAsState()
    val context = LocalContext.current

    FairThreadScaffold(navController, title = stringResource(R.string.payment)) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (paymentStatus) {
                PaymentViewModel.Status.IDLE -> {
                    Text(
                        stringResource(R.string.confirm_your_payment),
                        style = MaterialTheme.typography.h5,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { viewModel.processPayment(uid) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.confirm_payment))
                    }
                }
                PaymentViewModel.Status.PROCESSING -> {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(stringResource(R.string.processing_payment))
                }
                PaymentViewModel.Status.SUCCESS -> {
                    Text(stringResource(R.string.payment_successful))
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { navController.navigate("home") { popUpTo("payment") { inclusive = true } } },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.return_home))
                    }
                }
                PaymentViewModel.Status.ERROR -> {
                    Text(stringResource(R.string.payment_failed), color = MaterialTheme.colors.error)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { viewModel.reset() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.try_again))
                    }
                }
            }
        }
    }
}