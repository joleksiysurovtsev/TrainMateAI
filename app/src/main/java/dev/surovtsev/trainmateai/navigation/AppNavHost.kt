package dev.surovtsev.trainmateai.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.surovtsev.trainmateai.feature.calendar.CalendarScreen
import dev.surovtsev.trainmateai.feature.exercises.ExerciseScreen
import dev.surovtsev.trainmateai.feature.statistic.StatisticScreen
import dev.surovtsev.trainmateai.feature.tracker.TrackerScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Screen.Exercises.route, modifier = modifier) {
        composable(Screen.Exercises.route) { ExerciseScreen() }
        composable(Screen.Statistic.route) { StatisticScreen() }
        composable(Screen.Calendar.route) { CalendarScreen() }
        composable(Screen.Tracker.route) { TrackerScreen() }
    }
}