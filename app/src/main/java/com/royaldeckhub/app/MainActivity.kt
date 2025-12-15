package com.royaldeckhub.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.royaldeckhub.app.ui.MainScreen
import com.royaldeckhub.app.ui.theme.RoyalDeckHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoyalDeckHubTheme {
                MainScreen()
            }
        }
    }
}