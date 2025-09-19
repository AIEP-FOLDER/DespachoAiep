package com.lll.despachoaiep.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.lll.despachoaiep.model.UbicacionGps

fun guardarUbicacionEnFirebase(ubicacion: UbicacionGps) {
    val ref = FirebaseDatabase.getInstance().getReference("ubicaciones")
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "anonimo"
    ref.child(uid).push().setValue(ubicacion)
        .addOnSuccessListener {
            Log.i("Firebase", "Ubicación guardada correctamente")
        }
        .addOnFailureListener {
            Log.e("Firebase", "Error al guardar ubicación: ${it.message}")
        }
}
