package ru.ruscalworld.studyplanner.adapters

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.IconRow
import ru.ruscalworld.studyplanner.ui.theme.AppTypography
import ru.ruscalworld.studyplanner.ui.theme.PrimaryColor

@Composable
fun EstimatedTimeRow(
    hours: Int,
) {
    IconRow(
        icon = {
            Icon(
                painter = painterResource(R.drawable.fa_bolt_solid),
                tint = PrimaryColor,
                contentDescription = null,
            )
        }
    ) {
        Text(
            stringResource(
                R.string.common_task_card_time_estimation,
                hours,
                LocalContext.current.resources.getQuantityString(R.plurals.time_hours, hours),
            ),
            style = AppTypography.bodyMedium,
        )
    }
}
