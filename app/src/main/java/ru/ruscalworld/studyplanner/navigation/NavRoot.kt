package ru.ruscalworld.studyplanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ruscalworld.studyplanner.screens.diary.discipline.DisciplineScreen
import ru.ruscalworld.studyplanner.screens.diary.home.DiaryHomeScreen
import ru.ruscalworld.studyplanner.screens.diary.task.TaskScreen
import ru.ruscalworld.studyplanner.screens.editor.curriculum.CurriculumEditorScreen
import ru.ruscalworld.studyplanner.screens.editor.discipline.DisciplineEditorScreen
import ru.ruscalworld.studyplanner.screens.editor.task.TaskEditorScreen
import ru.ruscalworld.studyplanner.screens.options.info.InfoScreen
import ru.ruscalworld.studyplanner.screens.stats.home.StatsHomeScreen
import ru.ruscalworld.studyplanner.screens.welcome.curriculum.PickCurriculumScreen
import ru.ruscalworld.studyplanner.screens.welcome.start.StartScreen

@Composable
fun NavRoot(navController: NavHostController) {
    NavHost(navController, startDestination = "welcome/start") {
        composable("welcome/start") {
            StartScreen(
                navigateToNext = {
                    navController.navigate("welcome/curriculum") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        composable("welcome/curriculum") {
            PickCurriculumScreen(
                navigateToCurriculum = {
                    navController.navigate("diary/home") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        composable("diary/home") {
            DiaryHomeScreen(navigateTo = NavigateToImpl(navController, "diary"))
        }

        composable(
            "diary/disciplines/{disciplineId}",
            arguments = listOf(
                navArgument("disciplineId") { type = NavType.LongType },
            )
        ) { backStackEntry ->
            DisciplineScreen(
                disciplineId = backStackEntry.arguments?.getLong("disciplineId")!!,
            )
        }

        composable(
            "diary/disciplines/{disciplineId}/tasks/{taskId}",
            arguments = listOf(
                navArgument("disciplineId") { type = NavType.LongType },
                navArgument("taskId") { type = NavType.LongType },
            )
        ) { backStackEntry ->
            TaskScreen(
                disciplineId = backStackEntry.arguments?.getLong("disciplineId")!!,
                taskId = backStackEntry.arguments?.getLong("taskId")!!,
            )
        }

        composable("stats/home") {
            StatsHomeScreen()
        }

        composable("editor/curriculums/@current") {
            CurriculumEditorScreen()
        }

        composable(
            "editor/disciplines/{disciplineId}",
            arguments = listOf(
                navArgument("disciplineId") { type = NavType.LongType },
            )
        ) { backStackEntry ->
            DisciplineEditorScreen(
                disciplineId = backStackEntry.arguments?.getLong("disciplineId")!!,
            )
        }

        composable(
            "editor/disciplines/{disciplineId}/tasks/{taskId}",
            arguments = listOf(
                navArgument("disciplineId") { type = NavType.LongType },
            )
        ) { backStackEntry ->
            TaskEditorScreen(
                disciplineId = backStackEntry.arguments?.getLong("disciplineId")!!,
                taskId = backStackEntry.arguments?.getLong("taskId")!!,
            )
        }

        composable("options/info") {
            InfoScreen()
        }
    }
}
