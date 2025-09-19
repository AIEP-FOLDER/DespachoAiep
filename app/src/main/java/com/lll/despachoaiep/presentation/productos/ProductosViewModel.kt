package com.lll.despachoaiep.presentation.productos

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
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
    private val database = Firebase.database
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos


    init {
        getProductos()
    }

    private fun getProductos() {
        viewModelScope.launch {
            collectProductos().collect { snapshot ->
                val lista = mutableListOf<Producto>()
                for (productoSnapshot in snapshot.children) {
                    val producto = productoSnapshot.getValue(Producto::class.java)
                    producto?.let { lista.add(it) }
                    Log.i("Matias", "Producto leído: ${productoSnapshot.key} → ${producto?.nombre}")

                }
                _productos.value = lista
                Log.i("Matias", "Productos recibidos: ${lista.size}")
            }

        }


    }

    private fun collectProductos(): Flow<DataSnapshot> = callbackFlow {
        val ref = database.reference.child("productos")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Matias", "ERROR [getProductos]")
                close(error.toException())
            }

        }
        ref.addValueEventListener(listener)
        awaitClose {
            ref.removeEventListener(listener)
        }


    }
}