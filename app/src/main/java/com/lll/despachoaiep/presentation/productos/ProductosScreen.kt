package com.lll.despachoaiep.presentation.productos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.lll.despachoaiep.model.Producto
import com.lll.despachoaiep.presentation.components.topBar.CarritoViewModel


@Composable
fun ProductosScreen(
    viewModel: ProductosViewModel = viewModel(),
    carritoViewModel: CarritoViewModel,
) {
    val productos by viewModel.productos.collectAsState()

    Column {
        Text("Todos los productos", modifier = Modifier.padding(8.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(productos) { producto ->
                ProductoCard(producto, onAgregar = {
                    carritoViewModel.agregarProducto()
                })
            }
        }
    }



}


@Composable
fun ProductoCard(
    producto: Producto,
    onAgregar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .size(120.dp)
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .wrapContentWidth(Alignment.Start)
                ) {
                    Text(
                        text = "$${producto.precio}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 22.sp,
                        color = Color.Cyan
                    )
                    Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onAgregar,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .height(36.dp)

                    ) {
                        Text("+ Agregar")
                    }
                }

            }


        }
    }
}







// para subir los productos a firebase
/*
Button(
    onClick = {
        subirProductosIniciales()
        Log.i("Matias", "Carga masiva ejecutada")
    },
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
) {
    Text("Subir productos a Firebase")
}
 */