package com.royaldeckhub.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    enum class Screen {
        SPLASH, HOME, ABOUT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf(MainActivity.Screen.SPLASH) }

    // Navegación automática después del splash
    LaunchedEffect(Unit) {
        delay(2500) // 2.5 segundos para el splash
        currentScreen = MainActivity.Screen.HOME
    }

    when (currentScreen) {
        MainActivity.Screen.SPLASH -> SplashScreen()
        MainActivity.Screen.HOME -> HomeScreen(
            onAboutClick = { currentScreen = MainActivity.Screen.ABOUT }
        )
        MainActivity.Screen.ABOUT -> AboutScreen(
            onBack = { currentScreen = MainActivity.Screen.HOME }
        )
    }
}

@Composable
fun SplashScreen() {
    val scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 300
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF001F3F)), // Mismo color que splash_background
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_app),
                contentDescription = "Logo RoyalDeckHub",
                modifier = Modifier
                    .size(150.dp)
                    .scale(scale.value)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "RoyalDeckHub",
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun HomeScreen(onAboutClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido a RoyalDeckHub",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(onClick = onAboutClick) {
            Text("Sobre la aplicación")
        }
    }
}

@Composable
fun AboutScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "RoyalDeckHub",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "App para compartir y valorar mazos de Clash Royale.",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Versión: 0.1",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = onBack) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("contacto@royaldeckhub.com"))
                    putExtra(Intent.EXTRA_SUBJECT, "Mensaje desde RoyalDeckHub")
                    putExtra(Intent.EXTRA_TEXT, "Escribe aquí tu consulta...")
                }
                context.startActivity(intent)
            }
        ) {
            Text("Contactar por correo")
        }
    }
     }