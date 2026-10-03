package com.project40.memories.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import com.project40.memories.ui.theme.AccentGold
import com.project40.memories.ui.theme.ChampagneRose
import com.project40.memories.ui.theme.ChampagneRoseLight

@Composable
fun ScratchCard(
    isRevealed: Boolean,
    onProgress: (Float) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val scratchPoints = remember { mutableStateListOf<Offset>() }
    var totalCellsCovered by remember { mutableStateOf(0) }
    val visitedGrid = remember { mutableSetOf<Pair<Int, Int>>() }

    Box(modifier = modifier) {
        // Underlying secret reward
        content()

        // Foil overlay that gets scratched away
        if (!isRevealed) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = 0.99f) // Required for BlendMode.Clear to create transparency on canvas layer
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                scratchPoints.add(offset)
                                val cell = Pair((offset.x / 40f).toInt(), (offset.y / 40f).toInt())
                                if (visitedGrid.add(cell)) {
                                    totalCellsCovered = visitedGrid.size
                                    val approxPercent = (totalCellsCovered / 28f).coerceIn(0f, 1f)
                                    onProgress(approxPercent)
                                }
                            },
                            onDrag = { change, _ ->
                                change.consume()
                                val pt = change.position
                                scratchPoints.add(pt)
                                val cell = Pair((pt.x / 40f).toInt(), (pt.y / 40f).toInt())
                                if (visitedGrid.add(cell)) {
                                    totalCellsCovered = visitedGrid.size
                                    val approxPercent = (totalCellsCovered / 28f).coerceIn(0f, 1f)
                                    onProgress(approxPercent)
                                }
                            }
                        )
                    }
            ) {
                // 1. Draw luxurious golden-champagne foil texture
                val foilBrush = Brush.linearGradient(
                    colors = listOf(
                        ChampagneRoseLight,
                        AccentGold,
                        ChampagneRose,
                        AccentGold,
                        ChampagneRoseLight
                    )
                )
                drawRect(brush = foilBrush)

                // 2. Erase along touch paths using BlendMode.Clear
                if (scratchPoints.isNotEmpty()) {
                    val path = Path().apply {
                        moveTo(scratchPoints.first().x, scratchPoints.first().y)
                        for (i in 1 until scratchPoints.size) {
                            lineTo(scratchPoints[i].x, scratchPoints[i].y)
                        }
                    }

                    drawPath(
                        path = path,
                        color = Color.Transparent,
                        style = Stroke(
                            width = 65f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        ),
                        blendMode = BlendMode.Clear
                    )
                }
            }
        }
    }
}
