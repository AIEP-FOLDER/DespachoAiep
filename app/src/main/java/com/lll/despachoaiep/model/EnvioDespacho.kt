package com.lll.despachoaiep.model



enum class EstadoEntrega {
    Entregado,
    Reparto,
    ErrorEnvio
}



data class EnvioDespacho(
    val nombreUsuario: String,
    val correo: String,
    val direccionEntrega: String,
    val contactoEntrega: String,
    val latitud: Double,
    val longitud: Double,
    val incluyeCongelados: Boolean,
    val productosSeleccionados: List<Int>,
    val costoDespacho: Int,
    val totalEstimado: Int,
    val estadoEntrega: EstadoEntrega
)
