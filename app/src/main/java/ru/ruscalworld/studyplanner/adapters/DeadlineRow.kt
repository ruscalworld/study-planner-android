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
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.abs
import kotlin.math.ceil

@Composable
fun DeadlineRow(
    deadline: Instant,
) {
    val deadlineDays = getDeadlineDays(deadline)

    IconRow(
        icon = {
            Icon(
                painter = painterResource(R.drawable.fa_clock_solid),
                tint = PrimaryColor,
                contentDescription = null,
            )
        }
    ) {
        Text(
            when {
                deadlineDays > 0 -> stringResource(
                    R.string.common_task_card_deadline,
                    deadlineDays,
                    LocalContext.current.resources.getQuantityString(
                        R.plurals.time_days,
                        deadlineDays
                    ),
                    DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.MEDIUM)
                        .format(deadline.atZone(ZoneId.systemDefault()))
                )
                deadlineDays == 0 -> stringResource(
                    R.string.common_task_card_deadline_today,
                )
                else -> stringResource(
                    R.string.common_task_card_deadline_expired,
                    abs(deadlineDays),
                    LocalContext.current.resources.getQuantityString(
                        R.plurals.time_days,
                        abs(deadlineDays)
                    ),
                )
            },
            style = AppTypography.bodyMedium,
        )
    }
}

fun getDeadlineDays(deadline: Instant): Int {
    val remainingMillis: Long = deadline.toEpochMilli() - System.currentTimeMillis()
    return ceil((remainingMillis / 1000).toDouble() / 86400).toInt()
}
