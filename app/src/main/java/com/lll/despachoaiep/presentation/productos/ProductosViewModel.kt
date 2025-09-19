package com.lll.despachoaiep.presentation.productos

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.lll.despachoaiep.model.Producto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class ProductosViewModel : ViewModel() {
    //private val database = Firebase.database

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos


    init {
        cargarProductosDesdeFirebase()

    }

    private fun cargarProductosDesdeFirebase() {
        val ref = FirebaseDatabase.getInstance().getReference("productos_map")

        ref.get().addOnSuccessListener { snapshot ->
            val lista = mutableListOf<Producto>()

            // Leer como lista
            val productosList =
                snapshot.getValue(object : GenericTypeIndicator<List<Producto?>>() {})
                    ?: emptyList()

            productosList.forEachIndexed { index, producto ->
                if (producto != null) {
                    lista.add(producto)
                } else {
                    Log.w("Firebase", "Elemento nulo en índice $index")
                }
            }

            _productos.value = lista
        }.addOnFailureListener {
            Log.e("Firebase", "Error al leer productos: ${it.message}")
        }
    }


}