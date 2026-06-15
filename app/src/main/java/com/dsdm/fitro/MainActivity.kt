package com.dsdm.fitro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dsdm.fitro.ui.screens.AppNavigation
import com.dsdm.fitro.ui.theme.FitRoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitRoTheme {
                AppNavigation()
            }
        }
    }
}
