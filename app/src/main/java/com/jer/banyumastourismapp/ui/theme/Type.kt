package com.jer.banyumastourismapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.jer.banyumastourismapp.R


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

// Set of Material typography styles to start with
val Typography = Typography(

    displayLarge = TextStyle(fontFamily = displayFontFamily),
    displayMedium = TextStyle(fontFamily = displayFontFamily),
    displaySmall = TextStyle(fontFamily = displayFontFamily),
    headlineLarge = TextStyle(fontFamily = displayFontFamily),
    headlineMedium = TextStyle(fontFamily = displayFontFamily),
    headlineSmall = TextStyle(fontFamily = displayFontFamily),
    titleLarge = TextStyle(fontFamily = displayFontFamily),
    titleMedium = TextStyle(fontFamily = displayFontFamily),
    titleSmall = TextStyle(fontFamily = displayFontFamily),
    bodyLarge = TextStyle(fontFamily = bodyFontFamily),
    bodyMedium = TextStyle(fontFamily = bodyFontFamily),
    bodySmall = TextStyle(fontFamily = bodyFontFamily),
    labelLarge = TextStyle(fontFamily = bodyFontFamily),
    labelMedium = TextStyle(fontFamily = bodyFontFamily),
    labelSmall = TextStyle(fontFamily = bodyFontFamily),

//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)