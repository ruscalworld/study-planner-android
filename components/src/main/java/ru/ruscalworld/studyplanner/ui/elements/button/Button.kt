package ru.ruscalworld.studyplanner.ui.elements.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.ui.theme.AppTypography
import ru.ruscalworld.studyplanner.ui.theme.CardBackground
import ru.ruscalworld.studyplanner.ui.theme.PrimaryBackground
import ru.ruscalworld.studyplanner.ui.theme.PrimaryBorder
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText
import ru.ruscalworld.studyplanner.ui.theme.SurfaceText

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    content: @Composable () -> String
) {
    ElevatedButton(
        onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonColors(
            containerColor = PrimaryBackground,
            contentColor = SurfaceText,
            disabledContainerColor = CardBackground,
            disabledContentColor = PrimaryText,
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        border = BorderStroke(2.dp, PrimaryBorder),
        modifier = modifier.height(48.dp)
    ) {
        when (isLoading) {
            true -> CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 3.dp,
                color = SurfaceText
            )

            false -> Text(
                content(),
                style = AppTypography.displaySmall,
            )
        }
    }
}
