package com.project40.memories.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import com.project40.memories.ui.theme.AccentGold
import com.project40.memories.ui.theme.ChampagneRose
import com.project40.memories.ui.theme.DustyTerracotta
import com.project40.memories.ui.theme.MutedSage
import kotlin.random.Random

private data class Particle(
    val initialX: Float,
    val initialY: Float,
    val velocityX: Float,
    val velocityY: Float,
    val size: Float,
    val color: Color,
    val rotationSpeed: Float,
    val shapeType: Int // 0: rect, 1: circle
)

@Composable
fun ConfettiOverlay(
    trigger: Int,
    modifier: Modifier = Modifier
) {
    if (trigger == 0) return

    val progress = remember(trigger) { Animatable(0f) }
    var particles by remember(trigger) { mutableStateOf<List<Particle>>(emptyList()) }

    val colors = listOf(
        DustyTerracotta,
        ChampagneRose,
        AccentGold,
        MutedSage,
        Color(0xFFFFB4A2),
        Color(0xFFC8EBD7)
    )

    LaunchedEffect(trigger) {
        // Generate 75 burst particles
        particles = (0 until 75).map {
            Particle(
                initialX = 0.5f,
                initialY = 0.45f,
                velocityX = (Random.nextFloat() - 0.5f) * 1.8f,
                velocityY = -Random.nextFloat() * 1.5f - 0.3f,
                size = Random.nextFloat() * 14f + 8f,
                color = colors[Random.nextInt(colors.size)],
                rotationSpeed = (Random.nextFloat() - 0.5f) * 720f,
                shapeType = Random.nextInt(2)
            )
        }
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2400)
        )
    }

    if (progress.value < 1f) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val t = progress.value
            val alpha = (1f - t).coerceIn(0f, 1f)

            particles.forEach { p ->
                val gravity = 2.2f * t * t
                val currentX = (p.initialX * width) + (p.velocityX * width * 0.7f * t)
                val currentY = (p.initialY * height) + (p.velocityY * height * 0.7f * t) + (gravity * height * 0.5f)

                rotate(degrees = p.rotationSpeed * t, pivot = Offset(currentX, currentY)) {
                    if (p.shapeType == 0) {
                        drawRect(
                            color = p.color.copy(alpha = alpha),
                            topLeft = Offset(currentX - p.size / 2, currentY - p.size / 2),
                            size = Size(p.size, p.size * 0.6f)
                        )
                    } else {
                        drawCircle(
                            color = p.color.copy(alpha = alpha),
                            radius = p.size / 2.5f,
                            center = Offset(currentX, currentY)
                        )
                    }
                }
            }
        }
    }
}
