package dev.surovtsev.trainmateai.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
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

    val dest = navController.currentBackStackEntryAsState().value?.destination?.route       // gets the current route from the navigation controller.
    val selIndex = tabs.indexOfFirst { it.route == dest }.coerceAtLeast(0)        // finds the index of the tab corresponding to the current route, or 0 if the route is not found
    val hazeState = remember { HazeState() }
    val modifier: Modifier = Modifier
    val shape: Shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)

    // Creates an animated floating quantity that changes smoothly from the old to the new index.
    val idxAnim by animateFloatAsState(
        targetValue = selIndex.toFloat(),
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioLowBouncy
        )
    )

    // Animates the highlight color according to the selected tab.
    val glowColor by animateColorAsState(
        targetValue = tabs[selIndex].glow,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)                                                                   // fixed height
            .padding(bottom = 0.dp)                                                                 // indentation from below
            .clip(shape)                                                                            // trims the content according to the defined shape
            .background(ExtendedTheme.colors.customNavbar)                                       // adds dullness
            .hazeChild(                                                                             // Applies blur effect through Haze library
                state = hazeState,
                shape = shape,
                style = HazeStyle(
                    blurRadius = 60.dp,                                                             // Blur radius 60 dp
                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.25f),                                         // Adds a black tint with transparency 0.25
                    noiseFactor = 0.15f                                                             // Adds 15% noise for a more natural effect
                )
            )
    ) {

        // Creates a Canvas canvas for the entire size of the container.
        Canvas(
            Modifier
                .fillMaxSize()
                .blur(50.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)                 // adds blur to Canvas content.
        ) {
            val tabW = size.width / tabs.size                                                // calculates the width of one tab.
            drawCircle(                                                                             // draws a highlight circle:
                color = glowColor.copy(alpha = 0.6f),
                radius = size.height / 2.5f,                                                        // the radius of the circle
                center = Offset(tabW * idxAnim + tabW / 2, size.height / 2)
            )
        }

        // Creates a transparent surface that fills the entire container and has the same shape.
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
                // iterate through all tabs with their indices.
                tabs.forEachIndexed { i, tab ->
                    val selected = i == selIndex
                    val alpha by animateFloatAsState(targetValue = if (selected) 1f else 0.7f)
                    val scale by animateFloatAsState(
                        targetValue = if (selected) 1.3f else 0.98f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow,
                        )
                    )

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


