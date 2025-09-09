package com.lll.despachoaiep.presentation.model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val imagenUrl: String = ""
)
