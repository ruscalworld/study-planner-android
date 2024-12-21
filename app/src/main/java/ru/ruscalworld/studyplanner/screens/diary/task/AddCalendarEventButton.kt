package ru.ruscalworld.studyplanner.screens.diary.task

import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.ui.elements.card.CardButton
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText

@Composable
fun AddCalendarEventButton(
    eventName: String,
) {
    val context = LocalContext.current

    CardButton(
        onClick = {
            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, eventName)
            }

            context.startActivity(intent)
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.fa_calendar_solid),
                tint = PrimaryText,
                contentDescription = null,
            )
        },
    ) {
        stringResource(R.string.diary_task_status_calendar_event_button)
    }
}
