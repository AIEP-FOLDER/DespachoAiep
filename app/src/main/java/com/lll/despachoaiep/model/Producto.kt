package com.lll.despachoaiep.model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val imagenUrl: String = ""
)
