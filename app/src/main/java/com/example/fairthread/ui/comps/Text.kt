package com.example.fairthread.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.fairthread.ui.theme.WhiteText

@Composable
fun FairTextField(label: String, isPassword: Boolean = false) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label, color = WhiteText) },
        textStyle = TextStyle(color = WhiteText),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = WhiteText,
            unfocusedBorderColor = WhiteText,
            cursorColor = WhiteText,
            focusedLabelColor = WhiteText,
            unfocusedLabelColor = WhiteText
        )
    )
}