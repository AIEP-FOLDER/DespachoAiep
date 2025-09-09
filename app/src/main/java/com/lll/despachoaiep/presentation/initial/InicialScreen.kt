package com.lll.despachoaiep.presentation.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lll.despachoaiep.R
import com.lll.despachoaiep.presentation.signup.GoogleAuthClient
import com.lll.despachoaiep.ui.theme.BackgroundButton
import com.lll.despachoaiep.ui.theme.Black
import com.lll.despachoaiep.ui.theme.Gray
import com.lll.despachoaiep.ui.theme.Green
import com.lll.despachoaiep.ui.theme.ShapeButton
import kotlinx.coroutines.launch

@Preview
@Composable
fun InitialScreen(
    navigateToLogin: () -> Unit = {},
    navigateToSignUp: () -> Unit = {},
    onGoogleLoginSuccess: () -> Unit = {}
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val googleClient = remember { GoogleAuthClient(context) }

    LaunchedEffect(Unit) {
        if (googleClient.isSignedIn()) {
            onGoogleLoginSuccess()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Gray, Black), startY = 0f, endY = 600f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.variantlogo),
            contentDescription = "",
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text("Despacho AIEP", color = Color.White, fontSize = 38.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            modifier = Modifier,
            painterResource(id = R.drawable.google),
            "Continue with Google ",
            onClick = {
                scope.launch {
                    val success = googleClient.signIn()
                    if (success) {
                        onGoogleLoginSuccess()
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier.weight(1f))

    }
}

@Composable
fun CustomButton(modifier: Modifier, painter: Painter, title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp)
            .background(BackgroundButton)
            .border(2.dp, ShapeButton, CircleShape)
            .clickable { onClick() },

        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(16.dp)
        )
        Text(
            title,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}



