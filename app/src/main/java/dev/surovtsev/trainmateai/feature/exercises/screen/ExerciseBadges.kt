package dev.surovtsev.trainmateai.feature.exercises.screen

/* ---------- imports ---------- */
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import dev.surovtsev.trainmateai.feature.exercises.domain.ExerciseCategory
import dev.surovtsev.trainmateai.ui.theme.ExtendedTheme

/* ---------- внешний API ---------- */
@Composable
fun ExerciseBadges(
    selected: ExerciseCategory?,
    onCategorySelected: (ExerciseCategory?) -> Unit,
    isRefreshing: Boolean,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hazeState   = remember { HazeState() }
    val badgeShape  = RoundedCornerShape(50)
    val rootCategories = ExerciseCategory.ROOTS     // список корневых категорий

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /* ---------- FlowRow с категориями ---------- */
        FlowRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Companion.CenterHorizontally),
            verticalArrangement   = Arrangement.spacedBy(8.dp)
        ) {
            rootCategories.forEach { category ->
                val isSel = category == selected
                CategoryBadge(
                    text       = category.displayName,
                    baseColor  = category.color ?: MaterialTheme.colorScheme.primary,
                    selected   = isSel,
                    onClick    = {
                        onCategorySelected(
                            when {
                                category == ExerciseCategory.All -> null
                                isSel                            -> null
                                else                             -> category
                            }
                        )
                    },
                    hazeState = hazeState,
                    shape     = badgeShape
                )
            }
        }

        /* ---------- неоновая кнопка Refresh ---------- */
        RefreshBadge(
            isRefreshing  = isRefreshing,
            onClick       = onRefreshClick,
            baseColor     = MaterialTheme.colorScheme.primary,   // любой базовый оттенок
            hazeState     = hazeState,
            shape         = badgeShape,
            modifier      = Modifier.padding(start = 8.dp)
        )
    }
}

/* -------------------------------------------------------------------------- */
/* ------------------------- Отдельные мини-компоненты ---------------------- */
/* -------------------------------------------------------------------------- */

/* 1. Обычный текстовый бейдж категории */
@Composable
private fun CategoryBadge(
    text: String,
    baseColor: Color,
    selected: Boolean,
    onClick: () -> Unit,
    hazeState: HazeState,
    shape: Shape
) {
    val glowAlpha by animateFloatAsState(if (selected) 0.85f else 0.35f)
    val glowScale by animateFloatAsState(if (selected) 1.35f else 1.05f)

    NeonContainer(
        baseColor  = baseColor,
        alpha      = glowAlpha,
        scale      = glowScale,
        hazeState  = hazeState,
        shape      = shape,
        onClick    = onClick
    ) {
        Text(
            text  = text,
            color = ExtendedTheme.colors.badgeTextColor,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

/* 2. Иконка Refresh с вращением во время загрузки */
@Composable
private fun RefreshBadge(
    isRefreshing: Boolean,
    onClick: () -> Unit,
    baseColor: Color,
    hazeState: HazeState,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    val infinite = rememberInfiniteTransition(label = "spin")
    val angle by infinite.animateFloat(
        initialValue = 0f,
        targetValue  = 360f,
        animationSpec = infiniteRepeatable(
            tween(800, easing = LinearEasing),
            RepeatMode.Restart
        ), label = "angle"
    )

    NeonContainer(
        baseColor = baseColor,
        alpha     = 0.6f,
        scale     = 1.1f,
        hazeState = hazeState,
        shape     = shape,
        onClick   = { if (!isRefreshing) onClick() },
        modifier  = modifier
    ) {
        Icon(
            imageVector        = Icons.Filled.Refresh,
            contentDescription = "Обновить",
            tint   = ExtendedTheme.colors.badgeTextColor,
            modifier = Modifier
                .size(20.dp)
                .graphicsLayer { rotationZ = if (isRefreshing) angle else 0f }
        )
    }
}

/* 3. Общий «стеклянный неоновый» контейнер */
@Composable
private fun NeonContainer(
    baseColor: Color,
    alpha: Float,
    scale: Float,
    hazeState: HazeState,
    shape: Shape,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // glow
        Canvas(
            Modifier
                .matchParentSize()
                .blur(40.dp, BlurredEdgeTreatment.Unbounded)
        ) {
            val radius = size.minDimension / 2 * scale
            drawCircle(
                color  = baseColor.copy(alpha = alpha),
                radius = radius,
                center = center
            )
        }

        // glass + haze
        Row(
            modifier = Modifier
                .clip(shape)
                .background(ExtendedTheme.colors.customNavbar)
                .hazeChild(
                    state = hazeState,
                    shape = shape,
                    style = HazeStyle(
                        blurRadius = 60.dp,
                        tint       = Color.Black.copy(alpha = 0.25f),
                        noiseFactor = 0.15f
                    )
                )
                .clickable { onClick() }
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            content = content
        )
    }
}