package com.lll.despachoaiep.datos

import android.util.Log
import com.google.firebase.database.FirebaseDatabase

// CREAR O ACTUALIZAR
fun guardarRangosTemperatura(min: Double, max: Double) {
    val referencia = FirebaseDatabase.getInstance().getReference("temperatura_config")
    val datos = mapOf("min" to min, "max" to max)
    referencia.setValue(datos).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            // TODO: Poner toast
            Log.i("Matias", "Rangos guardados correctamente")
        } else {
            // TODO: Poner toast
            Log.e("Matias", "Error al guardar rangos: ${task.exception?.message}")
        }
    }
}

// leer rangos
fun obtenerRangosTemperatura(onResult: (Double, Double) -> Unit) {
    val referencia = FirebaseDatabase.getInstance().getReference("temperatura_config")
    referencia.get().addOnSuccessListener { snapshot ->
        val min = snapshot.child("min").getValue(Double::class.java) ?: -18.0
        val max = snapshot.child("max").getValue(Double::class.java) ?: -10.0
        onResult(min, max)
    }.addOnFailureListener {
        Log.e("Matias", "Error al leer rangos: ${it.message}")
    }
}

// ELIMINAR RANGOS DE TEMPERATUR
fun eliminarRangosTemperatura() {
    val referencia = FirebaseDatabase.getInstance().getReference("temperatura_config")
    referencia.removeValue().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            // TODO: Poner toast
            Log.i("Matias", "Rangos eliminados correctamente")
        } else {
            Log.e("Matias", "Error al eliminar rangos: ${task.exception?.message}")
        }
    }
}