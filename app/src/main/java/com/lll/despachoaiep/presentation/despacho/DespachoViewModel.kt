package com.lll.despachoaiep.presentation.despacho

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lll.despachoaiep.model.EnvioDespacho
import com.lll.despachoaiep.model.EstadoEntrega
import com.lll.despachoaiep.model.PedidoConEstado

class DespachoViewModel : ViewModel() {
    private val _pedidos = mutableStateListOf<PedidoConEstado>()
    val pedidos: List<PedidoConEstado> get() = _pedidos

    val pedidosEnReparto: List<PedidoConEstado>
        get() = pedidos.filter { it.estado == EstadoEntrega.Reparto }

    fun cargarPedidos(uid: String) {
        val ref = FirebaseDatabase.getInstance().getReference("envios").child(uid)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _pedidos.clear()
                for (pedidoSnapshot in snapshot.children) {
                    val envio = pedidoSnapshot.getValue(EnvioDespacho::class.java)
                    val pedidoId = pedidoSnapshot.key ?: continue

                    envio?.let {
                        val estadoEnum = EstadoEntrega.from(envio.estadoEntrega)

                        _pedidos.add(PedidoConEstado(envio, estadoEnum, pedidoId))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error al leer pedidos: ${error.message}")
            }
        })
    }

    fun actualizarEstado(uid: String, pedidoId: String, nuevoEstado: EstadoEntrega) {
        val ref = FirebaseDatabase.getInstance()
            .getReference("envios")
            .child(uid)
            .child(pedidoId)
            .child("estadoEntrega")

        ref.setValue(nuevoEstado.name)
            .addOnSuccessListener {
                Log.i("Firebase", "Estado actualizado a $nuevoEstado")
            }
            .addOnFailureListener {
                Log.e("Firebase", "Error al actualizar estado: ${it.message}")
            }
    }

}