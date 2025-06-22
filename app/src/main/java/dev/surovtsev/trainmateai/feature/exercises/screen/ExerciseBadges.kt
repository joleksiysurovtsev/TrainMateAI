package dev.surovtsev.trainmateai.feature.exercises.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import dev.surovtsev.trainmateai.feature.exercises.ExerciseCategory
import dev.surovtsev.trainmateai.ui.theme.ExtendedTheme

@Composable
fun ExerciseBadges(
    selected: ExerciseCategory?,
    onCategorySelected: (ExerciseCategory?) -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    val badgeShape: Shape = RoundedCornerShape(50)
    val rootCategories = ExerciseCategory.Companion.ROOTS
    val hazeState   = remember { HazeState() }

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Companion.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        rootCategories.forEach { category ->
            val isSelected = category == selected
            CategoryBadge(
                category = category,
                selected = isSelected,
                baseColor = category.color ?: MaterialTheme.colorScheme.primary,
                hazeState = hazeState,
                shape = badgeShape
            ) {
                onCategorySelected(
                    when {
                        category == ExerciseCategory.All -> null
                        isSelected -> null
                        else -> category
                    }
                )
            }
        }
    }
}


@Composable
private fun CategoryBadge(
    category: ExerciseCategory,
    selected: Boolean,
    baseColor: Color,
    hazeState: HazeState,
    shape: Shape,
    onClick: () -> Unit
) {
    /* There is always a light glow; when selected we amplify */
    val glowAlpha by animateFloatAsState(if (selected) 0.80f else 0.30f)
    val glowScale by animateFloatAsState(
        if (selected) 1.40f else 1.10f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box {
        // 1 Layer: FREE Canvas (do not clip anything)
        Canvas(
            Modifier.Companion
                .matchParentSize()
                .blur(40.dp, edgeTreatment = BlurredEdgeTreatment.Companion.Unbounded)
        ) {
            drawCircle(
                color = baseColor.copy(alpha = glowAlpha),
                radius = (size.minDimension / 2) * glowScale,
                center = center
            )
        }

        // 2 layer - the badge itself, already with clip + glass
        Box(
            modifier = Modifier.Companion
                .clip(shape)
                .background(ExtendedTheme.colors.customNavbar)
                .hazeChild(
                    state = hazeState,
                    shape = shape,
                    style = HazeStyle(
                        blurRadius = 60.dp,
                        tint = Color.Companion.Black.copy(alpha = 0.25f),
                        noiseFactor = 0.15f
                    )
                )
                .clickable(onClick = onClick)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = category.displayName,
                color = ExtendedTheme.colors.badgeTextColor,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}