package dev.surovtsev.trainmateai.feature.exercises

/**
 * Иерархия категорий упражнений.
 * Все списки / карты создаём лениво через lazy {}, чтобы избежать
 * обращения к объектам, которые ещё не успели инициализироваться.
 */
sealed class ExerciseCategory(val displayName: String) {

    /* ---- базовые ---- */
    object Chest     : ExerciseCategory("Chest")
    object Back      : ExerciseCategory("Back")
    object Shoulders : ExerciseCategory("Shoulders")
    object Core      : ExerciseCategory("Core")
    object Cardio    : ExerciseCategory("Cardio")
    object Other     : ExerciseCategory("Other")

    /* ---- Arms ---- */
    sealed class Arms(displayName: String) : ExerciseCategory(displayName) {
        object Biceps   : Arms("Biceps")
        object Triceps  : Arms("Triceps")
        object Forearms : Arms("Forearms")
    }

    /* ---- Legs ---- */
    sealed class Legs(displayName: String) : ExerciseCategory(displayName) {
        object Quads      : Legs("Quads")
        object Hamstrings : Legs("Hamstrings")
        object Calves     : Legs("Calves")
        object Glutes     : Legs("Glutes")
    }

    companion object {
        /** Плоский список всех категорий (создаётся при первом обращении). */
        val ALL: List<ExerciseCategory> by lazy {
            listOf(
                Chest, Back, Shoulders, Core, Cardio, Other,
                Arms.Biceps, Arms.Triceps, Arms.Forearms,
                Legs.Quads, Legs.Hamstrings, Legs.Calves, Legs.Glutes
            )
        }

        /** Карта displayName → объект (ленивая, чтобы не ловить NPE). */
        val BY_NAME: Map<String, ExerciseCategory> by lazy {
            ALL.associateBy { it.displayName }
        }
    }
}
