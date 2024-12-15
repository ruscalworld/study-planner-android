package ru.ruscalworld.studyplanner.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.ui.theme.PrimaryColor
import ru.ruscalworld.studyplanner.ui.theme.SecondaryText

@Composable
fun BottomNavigation(navController: NavHostController) {
    val navOptions: NavOptionsBuilder.() -> Unit = {
        popUpTo(navController.graph.startDestinationId) {
            inclusive = true
        }

        launchSingleTop = true
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = Modifier
            .safeGesturesPadding()
            .height(64.dp)
            .shadow(
                8.dp,
                RoundedCornerShape(10.dp),
                ambientColor = Color.Black,
                spotColor = Color.Black
            )
            .clip(RoundedCornerShape(10.dp)),

        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = { navController.navigate("diary/home", navOptions) }) {
                    Icon(
                        painter = painterResource(R.drawable.fa_book_solid),
                        contentDescription = stringResource(R.string.navigation_section_diary),
                        tint = navSectionColor(currentDestination, "diary"),
                        modifier = Modifier.size(24.dp),
                    )
                }

                IconButton(onClick = { navController.navigate("editor/curriculums/@current", navOptions) }) {
                    Icon(
                        painter = painterResource(R.drawable.fa_pencil_solid),
                        contentDescription = stringResource(R.string.navigation_section_editor),
                        tint = navSectionColor(currentDestination, "editor"),
                        modifier = Modifier.size(24.dp),
                    )
                }

                IconButton(onClick = { navController.navigate("options/info", navOptions) }) {
                    Icon(
                        painter = painterResource(R.drawable.fa_bars_solid),
                        contentDescription = stringResource(R.string.navigation_section_options),
                        tint = navSectionColor(currentDestination, "options"),
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        },
    )
}

fun navSectionColor(currentDestination: String?, targetPrefix: String): Color {
    if (currentDestination == null || !currentDestination.startsWith(targetPrefix)) return SecondaryText
    return PrimaryColor
}
