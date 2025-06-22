package dev.surovtsev.trainmateai.feature.exercises

import androidx.compose.ui.graphics.Color

enum class ExerciseCategory(
    val displayName: String,
    val parent: ExerciseCategory? = null,
    val defaultCollor: Color? = null
) {

    /* ----- basic ----- */
    Chest(displayName = "Chest", defaultCollor = Color(0xFFEF5350)),
    Back(displayName = "Back", defaultCollor = Color(0xFF64C4FF)),
    Arms(displayName = "Arms", defaultCollor = Color(0xFFFA6FFF)),
    Legs(displayName = "Legs", defaultCollor = Color(0xFF26A69A)),
    Core(displayName = "Core", defaultCollor = Color(0xFF66BB6A)),
    Cardio(displayName = "Cardio", defaultCollor = Color(0xFFFF7043)),
    All(displayName = "All", defaultCollor = Color(0xFF90A4AE)),


    /* ----- Arms + sub-categories ----- */
    Shoulders(displayName = "Shoulders", parent = Arms),
    Biceps(displayName = "Biceps", parent = Arms),
    Triceps(displayName = "Triceps", parent = Arms),
    Forearms(displayName = "Forearms", parent = Arms),

    /* ----- Legs + sub-categories ----- */

    Quads(displayName = "Quads", parent = Legs),
    Hamstrings(displayName = "Hamstrings", parent = Legs),
    Calves(displayName = "Calves", parent = Legs),
    Glutes(displayName = "Glutes", parent = Legs);

    /* ---------- helpers ---------- */

    /** Is the category "upper level". */
    val isRoot: Boolean get() = parent == null

    val color: Color get() = defaultCollor ?: parent!!.color

    /** Daughter categories of current (we count lazily through values ()). */
    val children: List<ExerciseCategory> by lazy {
        ExerciseCategory.entries.filter { it.parent == this }
    }

    companion object {

        /** All root categories (Chest, Back, Arms, Legs ...). */
        val ROOTS: List<ExerciseCategory> by lazy { ExerciseCategory.entries.filter { it.isRoot } }

        /** Quick search on displayName - need Saver'u on screen. */
        val BY_NAME: Map<String, ExerciseCategory> by lazy {
            ExerciseCategory.entries.associateBy { it.displayName }
        }
    }
}
