package com.example.giftpackinghelper.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val GiftRed = Color(0xFFFF3B30)

@Composable
fun MyApplicationTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        darkColorScheme(primary = GiftRed, background = Color.Black, onBackground = Color.White,
            surface = Color.Black, onSurface = Color.White, onSurfaceVariant = Color(0xFFABABAF))
    } else {
        lightColorScheme(primary = GiftRed, background = Color.White, onBackground = Color.Black,
            surface = Color.White, onSurface = Color.Black, onSurfaceVariant = Color(0xFF6C6C70))
    }
    MaterialTheme(colorScheme = colors, content = content)
}
