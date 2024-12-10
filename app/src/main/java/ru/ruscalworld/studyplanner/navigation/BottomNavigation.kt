package ru.ruscalworld.studyplanner.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder

@Composable
fun BottomNavigation(navController: NavHostController) {
    val navOptions: NavOptionsBuilder.() -> Unit = {
        popUpTo(navController.graph.startDestinationId) {
            inclusive = true
        }

        launchSingleTop = true
    }

    BottomAppBar(
        modifier = Modifier
            .safeGesturesPadding()
            .height(64.dp)
            .clip(RoundedCornerShape(10.dp)),

        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = { navController.navigate("diary/home", navOptions) }) {
                    Icon(Icons.Filled.Call, contentDescription = "Test")
                }
                IconButton(onClick = { navController.navigate("stats/home", navOptions) }) {
                    Icon(Icons.Filled.Call, contentDescription = "Test")
                }
                IconButton(onClick = { navController.navigate("editor/curriculums/@current", navOptions) }) {
                    Icon(Icons.Filled.Call, contentDescription = "Test")
                }
                IconButton(onClick = { navController.navigate("options/info", navOptions) }) {
                    Icon(Icons.Filled.Call, contentDescription = "Test")
                }
            }
        }
    )
}
