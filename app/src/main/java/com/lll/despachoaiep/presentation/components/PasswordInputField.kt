package com.lll.despachoaiep.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.lll.despachoaiep.ui.theme.SelectedField
import com.lll.despachoaiep.ui.theme.UnselectedField

@Composable
fun PasswordInputField(
    password: String, onPasswordChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

            Icon(
                imageVector = icon,
                contentDescription = description,
                tint = Color.White,
                modifier = Modifier.clickable { passwordVisible = !passwordVisible })
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = UnselectedField, focusedContainerColor = SelectedField
        )
    )
}