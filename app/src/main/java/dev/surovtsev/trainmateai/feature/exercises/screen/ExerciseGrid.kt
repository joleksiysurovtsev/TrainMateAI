package dev.surovtsev.trainmateai.feature.exercises.screen


/* ---------- imports ---------- */
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import dev.surovtsev.trainmateai.feature.exercises.domain.Exercise

/* -------------------------------------------------------------------------- */
/* ----------------------------- СЕТКА КАРТОЧЕК ----------------------------- */
/* -------------------------------------------------------------------------- */

/**
 * Сетка упражнений: теперь фиксированно 1 колонка,
 * поэтому каждая карточка занимает всю ширину.
 */
@Composable
fun ExerciseGrid(
    exerciseEntities: List<Exercise>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),                         // <─ одна колонка
        contentPadding = PaddingValues(
            start = 16.dp, end = 16.dp, top = 16.dp, bottom = 20.dp
        ),
        verticalArrangement   = Arrangement.spacedBy(12.dp), // промежуток между карточками
        horizontalArrangement = Arrangement.spacedBy(0.dp),  // не нужен
        modifier = modifier.fillMaxSize()
            .background(Color.Transparent)

    ) {
        items(exerciseEntities, key = { it.id }) {
            ExerciseTile(it)
        }
    }
}

@Composable
private fun ExerciseTile(exercise: Exercise) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            ExerciseImage(
                imageurl = exercise.imageurl ?: "https://via.placeholder.com/150",
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )

            /* --------- ПРАВАЯ СТОРОНА: Текст --------- */
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(16.dp)
            ) {

                // Название на ярком фоне
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF00BCD4), RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )

                // Краткое описание (3 строки)
                Text(
                    text = exercise.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )

                // Категория
                Text(
                    text = exercise.category.displayName,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}


@Composable
fun ExerciseImage(
    imageurl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)

    AsyncImage(
        model = imageurl,
        contentDescription = null,
        imageLoader = imageLoader,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
            .background(Color.LightGray)
    )
}