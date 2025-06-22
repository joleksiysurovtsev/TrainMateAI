package dev.surovtsev.trainmateai.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF00284D),
    primaryContainer = Color(0xFF1E88E5),
    onPrimaryContainer = Color(0xFFFFFFFF),
    inversePrimary = Color(0xFF1E88E5),

    secondary = Color(0xFFE1BEE7),
    onSecondary = Color(0xFF3D0049),
    secondaryContainer = Color(0xFF8E24AA),
    onSecondaryContainer = Color(0xFFFFFFFF),

    tertiary = Color(0xFFE6EE9C),
    onTertiary = Color(0xFF223A00),
    tertiaryContainer = Color(0xFFCDDC39),
    onTertiaryContainer = Color(0xFF000000),

    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),

    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),

    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFB0B0B0),

    surfaceTint = Color(0xFF90CAF9),
    inverseSurface = Color(0xFFE0E0E0),
    inverseOnSurface = Color(0xFF1E1E1E),

    error = Color(0xFFCF6679),
    onError = Color(0xFF3B0000),
    errorContainer = Color(0xFFB00020),
    onErrorContainer = Color(0xFFFFFFFF),

    outline = Color(0xFF444444),
    outlineVariant = Color(0xFF666666),
    scrim = Color(0x66000000),

    surfaceBright = Color(0xFF2A2A2A),
    surfaceDim = Color(0xFF101010),
    surfaceContainerLowest = Color(0xFF0F0F0F),
    surfaceContainerLow = Color(0xFF171717),
    surfaceContainer = Color(0xFF1C1C1C),
    surfaceContainerHigh = Color(0xFF222222),
    surfaceContainerHighest = Color(0xFF272727)
)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1E88E5),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD0E4FF),
    onPrimaryContainer = Color(0xFF001D36),
    inversePrimary = Color(0xFF90CAF9),

    secondary = Color(0xFF8E24AA),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFF7E4FB),
    onSecondaryContainer = Color(0xFF37003D),

    tertiary = Color(0xFFCDDC39),
    onTertiary = Color(0xFF263300),
    tertiaryContainer = Color(0xFFF5FBCF),
    onTertiaryContainer = Color(0xFF112100),

    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),

    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),

    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),

    surfaceTint = Color(0xFF1E88E5),
    inverseSurface = Color(0xFF313033),
    inverseOnSurface = Color(0xFFF4EFF4),

    error = Color(0xFFB00020),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF370002),

    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFC4C7CE),
    scrim = Color(0x66000000),

    surfaceBright = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFDEDAD9),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF7F2F9),
    surfaceContainer = Color(0xFFF3EDF7),
    surfaceContainerHigh = Color(0xFFECE6F3),
    surfaceContainerHighest = Color(0xFFE6DFEB)
)


data class ExtendedColorScheme(
    val colorScheme: androidx.compose.material3.ColorScheme,
    val customNavbar: Color,
    val customIcon: Color,
    val customText: Color,
    val badgeTextColor: Color,
)


@Composable
fun TrainMateAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val extendedColorScheme = ExtendedColorScheme(
        colorScheme = colorScheme,
        customNavbar = if (darkTheme) Color(0xFF151414) else Color(0x99FFF8F0),
        customIcon = if (darkTheme) Color(0xFFFFFFFF).copy(alpha = 1f) else Color(0xFF050505).copy(alpha = 0.75f),
        customText = if (darkTheme) Color(0xFFFFFFFF).copy(alpha = 1f) else Color(0xFF050505).copy(alpha = 0.75f),
        badgeTextColor = if (darkTheme) Color(0xFFFFFFFF).copy(alpha = 1f) else Color(0xFF050505).copy(alpha = 0.75f),
    )

    CompositionLocalProvider(
        LocalExtendedColorScheme provides extendedColorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }

}

val LocalExtendedColorScheme = staticCompositionLocalOf<ExtendedColorScheme> {
    error("ExtendedColorScheme not provided")
}

object ExtendedTheme {
    val colors: ExtendedColorScheme
        @Composable
        get() = LocalExtendedColorScheme.current
}
