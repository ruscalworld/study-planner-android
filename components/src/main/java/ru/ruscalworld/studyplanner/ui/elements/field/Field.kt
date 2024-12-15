package ru.ruscalworld.studyplanner.ui.elements.field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText
import ru.ruscalworld.studyplanner.ui.theme.SecondaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Field(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: (@Composable () -> String)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    lines: Int = 1,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = TextFieldDefaults.colors(
        unfocusedTextColor = SecondaryText,
        unfocusedLabelColor = SecondaryText,
        unfocusedPlaceholderColor = SecondaryText,
        unfocusedContainerColor = Color.Transparent,
        unfocusedIndicatorColor = SecondaryText,
        focusedTextColor = PrimaryText,
        focusedContainerColor = PrimaryText,
    )

    BasicTextField(
        modifier = modifier,
        cursorBrush = SolidColor(Color.White),
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = Color.White, fontSize = TextUnit(18f, TextUnitType.Sp)),
        keyboardOptions = keyboardOptions,
        maxLines = lines,
        minLines = lines,
        singleLine = lines == 1,
        decorationBox = @Composable { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value.text,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = lines == 1,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                isError = false,
                label = {
                    if (label != null)
                        Text(
                            label(),
                            fontSize = TextUnit(16f, TextUnitType.Sp),
                        )
                },
                placeholder = {},
                leadingIcon = null,
                trailingIcon = null,
                prefix = null,
                suffix = null,
                supportingText = null,
                colors = colors,
                contentPadding = PaddingValues(24.dp, 14.dp),
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = true,
                        isError = false,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = RoundedCornerShape(10.dp),
                        focusedBorderThickness = 2.dp,
                        unfocusedBorderThickness = 2.dp,
                    )
                },
            )
        }
    )
}
