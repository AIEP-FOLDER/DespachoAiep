package com.lll.despachoaiep.utils

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.lll.despachoaiep.datos.productos
import com.lll.despachoaiep.model.Producto

fun subirProductosIniciales() {
    val mapa = productos.associateBy { it.id.toString() }
    val ref = FirebaseDatabase.getInstance().getReference("productos_map")

    ref.setValue(mapa)
        .addOnSuccessListener {
            Log.i("Firebase", "Productos subidos correctamente como mapa")
        }
        .addOnFailureListener {
            Log.e("Firebase", "Error al subir productos: ${it.message}")
        }
}












