package dev.surovtsev.trainmateai.navigation

sealed class Screen(val route: String) {
    object Statistic : Screen("statistic")
    object Exercises : Screen("exercises")
    object Calendar : Screen("calendar")
    object Tracker : Screen("tracker")
}
