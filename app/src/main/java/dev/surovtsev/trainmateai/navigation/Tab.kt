package dev.surovtsev.trainmateai.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Tab(
    val label: String,
    val route: String,
    val icon: ImageVector,
    val glow: Color
)