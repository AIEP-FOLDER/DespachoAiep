package com.lll.despachoaiep.model


enum class EstadoEntrega {
    Entregado,
    Reparto,
    ErrorEnvio
}


data class EnvioDespacho(
    val nombreUsuario: String = "",
    val correo: String = "",
    val direccionEntrega: String = "",
    val contactoEntrega: String = "",
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val incluyeCongelados: Boolean = false,
    val productosSeleccionados: List<Int> = emptyList(),
    val costoDespacho: Int = 0,
    val totalEstimado: Int = 0,
    val estadoEntrega: String = EstadoEntrega.Reparto.name
)


data class PedidoConEstado(
    val envio: EnvioDespacho,
    val estado: EstadoEntrega,
    val pedidoId: String
)
