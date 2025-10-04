package com.lll.despachoaiep.presentation.admin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth


import com.lll.despachoaiep.datos.eliminarRangosTemperatura
import com.lll.despachoaiep.datos.guardarRangosTemperatura
import com.lll.despachoaiep.datos.obtenerRangosTemperatura


@Composable
fun PantallaAdmin(auth: FirebaseAuth) {
    var minTemp by remember { mutableStateOf("") }
    var maxTemp by remember { mutableStateOf("") }
    var rangoActual by remember { mutableStateOf<Pair<Double, Double>?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    // obtener temperatur min y max desde firebase
    LaunchedEffect(Unit) {
        obtenerRangosTemperatura { min, max ->
            minTemp = min.toString()
            maxTemp = max.toString()
            rangoActual = Pair(min, max)
        }
    }
    LazyColumn {
        // Add a single item
        item {
            // RANGOS DE TEMPERATURA
            RangoTemperaturasView(
                minTemp = minTemp,
                maxTemp = maxTemp,
                onMinChange = { minTemp = it },
                onMaxChange = { maxTemp = it },
                onGuardar = {
                    val min = minTemp.toDoubleOrNull()
                    val max = maxTemp.toDoubleOrNull()
                    if (min != null && max != null) {
                        guardarRangosTemperatura(min, max)
                        Toast.makeText(context, "Rangos guardados", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Valores inválidos", Toast.LENGTH_SHORT).show()
                    }
                },
                onEliminar = {
                    eliminarRangosTemperatura()
                    rangoActual = null
                    Toast.makeText(context, "Rangos eliminados", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }


}
