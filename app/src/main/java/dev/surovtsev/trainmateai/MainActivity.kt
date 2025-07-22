package dev.surovtsev.trainmateai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.surovtsev.trainmateai.navigation.AppNavHost
import dev.surovtsev.trainmateai.navigation.GlassBottomNavigationBar
import dev.surovtsev.trainmateai.ui.theme.ExtendedTheme
import dev.surovtsev.trainmateai.ui.theme.TrainMateAITheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TrainMateAITheme {
                val navController = rememberNavController()
                Scaffold(
                    containerColor = ExtendedTheme.colors.background,
                    bottomBar = {
                        GlassBottomNavigationBar(
                            navController = navController
                        )
                    }
                ) { padding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier
                            .padding(padding)
                            .background(Color.Transparent)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

