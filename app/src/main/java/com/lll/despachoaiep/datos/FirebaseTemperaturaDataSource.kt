package com.lll.despachoaiep.datos

import com.google.firebase.database.FirebaseDatabase
import com.lll.despachoaiep.model.LecturaTemperatura
import okhttp3.Callback

class FirebaseTemperaturaDataSource {
    private val dbRef = FirebaseDatabase.getInstance()
        // en este caso leeremos solo del "camion_001"
        .getReference("lecturasTemperatura/camion_001/ultimaLectura")

    fun obtenerUltimaLectura(callback: (LecturaTemperatura?) -> Unit){
        dbRef.get().addOnSuccessListener { snapshot ->
            val lectura = snapshot.getValue(LecturaTemperatura::class.java)
            callback(lectura)
        }.addOnFailureListener {
            callback(null)
        }
    }
}