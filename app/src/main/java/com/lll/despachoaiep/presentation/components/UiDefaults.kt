package com.lll.despachoaiep.presentation.components

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun textFieldColorsElegantes(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = Color.DarkGray,
        unfocusedContainerColor = Color.Gray,
        disabledContainerColor = Color.Gray.copy(alpha = 0.3f),
        focusedLabelColor = Color.White,
        unfocusedLabelColor = Color.LightGray,
        disabledLabelColor = Color.LightGray,
        cursorColor = Color.White,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
}

