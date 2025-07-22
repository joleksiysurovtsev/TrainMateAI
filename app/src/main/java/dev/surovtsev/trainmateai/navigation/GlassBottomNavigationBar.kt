package dev.surovtsev.trainmateai.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import dev.surovtsev.trainmateai.R
import dev.surovtsev.trainmateai.ui.theme.ExtendedTheme


@Composable
fun GlassBottomNavigationBar(
    navController: NavHostController,
) {
    // List of tabs is created inside the composable because vectorResource is a composable function.
    val tabs = listOf(
        Tab(
            "Statistic",
            Screen.Statistic.route,
            ImageVector.vectorResource(R.drawable.navbar_statistic),
            Color(0xFFFFA574)
        ),
        Tab(
            "Exercises",
            Screen.Exercises.route,
            ImageVector.vectorResource(R.drawable.navbar_exercises),
            Color(0xFFFA6FFF)
        ),
        Tab(
            "Calendar",
            Screen.Calendar.route,
            ImageVector.vectorResource(R.drawable.navbar_calendar),
            Color(0xFFADFF64)
        ),
        Tab(
            "Tracker",
            Screen.Tracker.route,
            ImageVector.vectorResource(R.drawable.navbar_tracker),
            Color(0xFF64C4FF)
        )
    )

    val dest = navController.currentBackStackEntryAsState().value?.destination?.route
    val selIndex = tabs.indexOfFirst { it.route == dest }.coerceAtLeast(0)
    val hazeState = remember { HazeState() }
    val shape: Shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)

    val idxAnim by animateFloatAsState(
        targetValue = selIndex.toFloat(),
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioLowBouncy
        ),
        label = "IndexAnimation"
    )

    val glowColor by animateColorAsState(
        targetValue = tabs[selIndex].glow,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "GlowColorAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(bottom = 0.dp)
            .clip(shape)
            .background(ExtendedTheme.colors.customNavbar)
            .hazeChild(
                state = hazeState,
                shape = shape,
                style = HazeStyle(
                    blurRadius = 50.dp,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.25f),
                    noiseFactor = 0.15f
                )
            )
    ) {
        // The blur modifier on the Canvas was removed to avoid double-blurring,
        // as hazeChild already provides the desired glass effect, improving performance.
        Canvas(Modifier.fillMaxSize()) {
            val tabW = size.width / tabs.size
            drawCircle(
                color = glowColor.copy(alpha = 0.6f),
                radius = size.height / 2.5f,
                center = Offset(tabW * idxAnim + tabW / 2, size.height / 2)
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent,
            shape = shape
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .border(
                        Dp.Hairline,
                        Brush.verticalGradient(
                            0f to Color.White.copy(alpha = 0.8f),
                            1f to Color.White.copy(alpha = 0.2f)
                        ),
                        shape
                    )
            ) {
                tabs.forEachIndexed { i, tab ->
                    val selected = i == selIndex

                    // Using updateTransition to synchronize multiple animations (alpha and scale).
                    // This provides a more cohesive and smoother visual effect when a tab is selected.
                    val transition = updateTransition(selected, label = "TabTransition")

                    val alpha by transition.animateFloat(label = "Alpha") { isSelected ->
                        if (isSelected) 1f else 0.7f
                    }
                    val scale by transition.animateFloat(
                        label = "Scale",
                        transitionSpec = {
                            spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow,
                            )
                        }
                    ) { isSelected ->
                        if (isSelected) 1.3f else 0.98f
                    }

                    Column(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .scale(scale)
                            .alpha(alpha)
                            .clickable {
                                if (dest != tab.route) {
                                    navController.navigate(tab.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            tint = ExtendedTheme.colors.customIcon,
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            text = tab.label,
                            color = ExtendedTheme.colors.customText,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}


