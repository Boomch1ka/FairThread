package com.example.fairthread.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.ui.theme.FairThreadTheme

@Composable
fun PreviewWrapper(content: @Composable () -> Unit) {
    FairThreadTheme {
        content()
    }
}