package com.example.fairthread.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.fairthread.ui.theme.WhiteText

@Composable
fun FairTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = WhiteText) },
        textStyle = TextStyle(color = WhiteText),
        modifier = modifier,
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = WhiteText,
            unfocusedBorderColor = WhiteText.copy(alpha = 0.5f),
            cursorColor = WhiteText,
            focusedLabelColor = WhiteText,
            unfocusedLabelColor = WhiteText.copy(alpha = 0.7f)
        )
    )
}
