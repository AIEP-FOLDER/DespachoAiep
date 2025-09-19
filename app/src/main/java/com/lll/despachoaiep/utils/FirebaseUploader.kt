package com.lll.despachoaiep.utils

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.lll.despachoaiep.model.Producto

fun subirProductosIniciales(productos: List<Producto>) {
    val ref = FirebaseDatabase.getInstance().getReference("productos")

    val mapa = productos.associateBy { it.id.toString() }
    ref.setValue(mapa)
        .addOnSuccessListener {
            Log.i("Matias", "Carga masiva exitosa")
        }
        .addOnFailureListener {
            Log.e("Matias", "Error al subir productos: ${it.message}")
        }
}



