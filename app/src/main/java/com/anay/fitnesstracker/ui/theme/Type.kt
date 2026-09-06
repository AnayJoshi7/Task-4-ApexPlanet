package com.anay.fitnesstracker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.anay.fitnesstracker.R

val SfProRounded = FontFamily(
    Font(R.font.sf_pro_rounded_regular, FontWeight.Normal),
    Font(R.font.sf_pro_rounded_medium, FontWeight.Medium),
    Font(R.font.sf_pro_rounded_semibold, FontWeight.SemiBold),
    Font(R.font.sf_pro_rounded_bold, FontWeight.Bold)
)

val Typography = Typography(
    displayLarge = TextStyle(fontFamily = SfProRounded),
    displayMedium = TextStyle(fontFamily = SfProRounded),
    displaySmall = TextStyle(fontFamily = SfProRounded),
    headlineLarge = TextStyle(fontFamily = SfProRounded),
    headlineMedium = TextStyle(fontFamily = SfProRounded),
    headlineSmall = TextStyle(fontFamily = SfProRounded),
    titleLarge = TextStyle(fontFamily = SfProRounded),
    titleMedium = TextStyle(fontFamily = SfProRounded),
    titleSmall = TextStyle(fontFamily = SfProRounded),
    bodyLarge = TextStyle(fontFamily = SfProRounded),
    bodyMedium = TextStyle(fontFamily = SfProRounded),
    bodySmall = TextStyle(fontFamily = SfProRounded),
    labelLarge = TextStyle(fontFamily = SfProRounded),
    labelMedium = TextStyle(fontFamily = SfProRounded),
    labelSmall = TextStyle(fontFamily = SfProRounded)
)