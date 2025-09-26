package com.lll.despachoaiep.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.lll.despachoaiep.model.EnvioDespacho

fun guardarEnvioCompleto(envio: EnvioDespacho) {
    val ref = FirebaseDatabase.getInstance().getReference("envios")
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "anonimo"
    ref.child(uid).push().setValue(envio)
        .addOnSuccessListener {
            Log.i("Firebase", "Envío guardado correctamente")
        }
        .addOnFailureListener {
            Log.e("Firebase", "Error al guardar envío: ${it.message}")
        }
}