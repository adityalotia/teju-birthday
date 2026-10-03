package com.project40.memories.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = DustyTerracotta,
    onPrimary = CrispOffWhite,
    primaryContainer = DustyTerracottaDark,
    onPrimaryContainer = CrispOffWhite,
    secondary = ChampagneRose,
    onSecondary = CrispOffWhite,
    secondaryContainer = ChampagneRoseLight,
    onSecondaryContainer = InkEspresso,
    tertiary = MutedSage,
    onTertiary = CrispOffWhite,
    tertiaryContainer = MutedSageDeep,
    onTertiaryContainer = CrispOffWhite,
    background = WarmAlabaster,
    onBackground = InkEspresso,
    surface = WarmAlabaster,
    onSurface = InkEspresso,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = InkCharcoal,
    outline = BorderWarm,
    outlineVariant = OutlineVariant
)

@Composable
fun Project40Theme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = WarmAlabaster.toArgb()
                window.navigationBarColor = WarmAlabaster.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = true
                controller.isAppearanceLightNavigationBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Project40Typography,
        shapes = Project40Shapes,
        content = content
    )
}
