package com.lll.despachoaiep.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lll.despachoaiep.model.TipoAnimacionLottie
import kotlinx.coroutines.delay

@Composable
fun AnimacionLottiePantallaCompleta(
    tipo: TipoAnimacionLottie,
    onOcultar: (() -> Unit)? = null
) {
    if (tipo != TipoAnimacionLottie.Ninguna) {
        val (mensaje, assetName) = when (tipo) {
            TipoAnimacionLottie.Confirmacion -> "¡Pedido realizado con éxito!" to "cartsuccess.json"
            TipoAnimacionLottie.Error -> "¡Error al procesar el pedido!" to "error_result.json"
            TipoAnimacionLottie.Ninguna -> return // no mostrar nada
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.95f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.Asset(assetName)
                )

                LottieAnimation(
                    composition = composition,
                    iterations = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = mensaje,
                    color = Color.White,
                    fontSize = 22.sp
                )
            }
        }

        LaunchedEffect(Unit) {
            delay(3000)
            onOcultar?.invoke()
        }
    }
}
