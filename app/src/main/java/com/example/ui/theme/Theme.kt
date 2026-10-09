package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
  primary = WearWhimPurple,
  onPrimary = Color.White,
  primaryContainer = WearWhimPale,
  onPrimaryContainer = WearWhimPurpleDark,
  secondary = WearWhimPurpleLight,
  onSecondary = WearWhimText,
  background = WearWhimBackground,
  onBackground = WearWhimText,
  surface = WearWhimSurface,
  onSurface = WearWhimText,
  surfaceVariant = WearWhimSurfaceVariant,
  onSurfaceVariant = WearWhimMuted,
  outline = WearWhimBorder,
  error = WearWhimRose
)

private val DarkColorScheme = darkColorScheme(
  primary = WearWhimPurpleLight,
  onPrimary = WearWhimPurpleDark,
  primaryContainer = WearWhimPurple,
  onPrimaryContainer = Color.White,
  secondary = WearWhimPale,
  onSecondary = Color.Black,
  background = Color(0xFF19161D),
  onBackground = Color(0xFFEDE7F6),
  surface = Color(0xFF221E28),
  onSurface = Color(0xFFEDE7F6),
  surfaceVariant = Color(0xFF2E2936),
  onSurfaceVariant = Color(0xFFCBC4CF),
  outline = Color(0xFF4A4452)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep WearWhim signature purple aesthetic
  content: @Composable () -> Unit,
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
