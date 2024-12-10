package ru.ruscalworld.studyplanner.ui.elements.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ruscalworld.studyplanner.ui.theme.AppTypography
import ru.ruscalworld.studyplanner.ui.theme.CardBackground
import ru.ruscalworld.studyplanner.ui.theme.CardBorder
import ru.ruscalworld.studyplanner.ui.theme.PrimaryBackground
import ru.ruscalworld.studyplanner.ui.theme.PrimaryBorder
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText
import ru.ruscalworld.studyplanner.ui.theme.SurfaceText

@Composable
fun CardButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    content: @Composable () -> String
) {
    ElevatedButton(
        onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonColors(
            containerColor = CardBackground,
            contentColor = PrimaryText,
            disabledContainerColor = CardBackground,
            disabledContentColor = PrimaryText,
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        border = BorderStroke(2.dp, CardBorder),
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Box(modifier = Modifier.width(24.dp)) {
                icon()
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(content(), style = AppTypography.displayMedium)
            }
        }
    }
}
