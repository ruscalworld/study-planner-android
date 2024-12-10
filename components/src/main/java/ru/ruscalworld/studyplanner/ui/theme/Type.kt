package ru.ruscalworld.studyplanner.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import ru.ruscalworld.studyplanner.ui.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Rubik"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Rubik"),
        fontProvider = provider,
        weight = FontWeight.Medium,
    )
)

val headlineFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Rubik"),
        fontProvider = provider,
        weight = FontWeight.SemiBold,
    )
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(
        fontFamily = displayFontFamily,
        fontSize = TextUnit(22f, TextUnitType.Sp),
        lineHeight = TextUnit(1.3f, TextUnitType.Em),
    ),
    displayMedium = baseline.displayMedium.copy(
        fontFamily = displayFontFamily,
        fontSize = TextUnit(20f, TextUnitType.Sp),
        lineHeight = TextUnit(1.3f, TextUnitType.Em),
    ),
    displaySmall = baseline.displaySmall.copy(
        fontFamily = displayFontFamily,
        fontSize = TextUnit(18f, TextUnitType.Sp),
        lineHeight = TextUnit(1.3f, TextUnitType.Em),
    ),
    headlineLarge = baseline.headlineLarge.copy(
        fontFamily = headlineFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TextUnit(28f, TextUnitType.Sp),
        color = PrimaryColor,
    ),
    headlineMedium = baseline.headlineMedium.copy(
        fontFamily = headlineFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TextUnit(28f, TextUnitType.Sp),
        color = PrimaryColor,
        lineHeight = TextUnit(1.3f, TextUnitType.Em),
    ),
    headlineSmall = baseline.headlineSmall.copy(
        fontFamily = headlineFontFamily,
    ),
    titleLarge = baseline.titleLarge.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TextUnit(28f, TextUnitType.Sp),
        lineHeight = TextUnit(1.3f, TextUnitType.Em),
    ),
    titleMedium = baseline.titleMedium.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TextUnit(26f, TextUnitType.Sp),
        lineHeight = TextUnit(1.3f, TextUnitType.Em),
    ),
    titleSmall = baseline.titleSmall.copy(fontFamily = headlineFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
    bodyMedium = baseline.bodyMedium.copy(
        fontFamily = bodyFontFamily,
        fontSize = TextUnit(18f, TextUnitType.Sp),
    ),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    labelMedium = baseline.labelMedium.copy(
        fontFamily = bodyFontFamily,
        color = SecondaryText,
        fontSize = TextUnit(18f, TextUnitType.Sp)
    ),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
)

