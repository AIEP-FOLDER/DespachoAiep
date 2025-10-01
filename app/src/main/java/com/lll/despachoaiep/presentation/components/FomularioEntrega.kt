package com.lll.despachoaiep.presentation.components

import android.text.Layout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun FormularioEntrega(
    direccion: String,
    contacto: String,
    incluyeCongelados: Boolean,
    onDireccionChange: (String) -> Unit,
    onContactoChange: (String) -> Unit,
    onCongeladosChange: (Boolean) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                // aqui la variable cambia segun el check
                checked = incluyeCongelados,
                onCheckedChange = onCongeladosChange
            )
            Text("¿Incluye productos congelados?", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = direccion,
            onValueChange = onDireccionChange,
            label = { Text("Dirección de entrega") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColorsElegantes()
        )

        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = contacto,
            onValueChange = { nuevoValor ->
                if (nuevoValor.all { it.isDigit() }) {
                    onContactoChange(nuevoValor)
                }
            },
            label = { Text("Teléfono") },
            placeholder = {Text("Ej: 912345678")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColorsElegantes()
        )
    }
}