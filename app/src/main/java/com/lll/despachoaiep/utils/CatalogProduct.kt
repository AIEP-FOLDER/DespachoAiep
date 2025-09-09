package com.lll.despachoaiep.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lll.despachoaiep.presentation.model.Producto
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage


@Composable
fun CatalogoProductosBurbuja(
    productos: List<Producto>,
    productosSeleccionados: List<Int>,
    onToggleProducto: (Producto) -> Unit

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp)
    ) {
        Text(
            text = "Productos disponibles",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        productos.forEach { producto ->
            val seleccionado = productosSeleccionados.contains(producto.id)


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable { onToggleProducto(producto) }
            ) {
                AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(
                            width = 3.dp,
                            color = if (seleccionado) Color.Blue else Color.Red,
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = producto.nombre, color = Color.White)
                Text(text = "$${producto.precio}", color = Color.LightGray)
            }
        }
    }
}
