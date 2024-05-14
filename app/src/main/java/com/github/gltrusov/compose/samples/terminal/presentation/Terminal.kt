package com.github.gltrusov.compose.samples.terminal.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import com.github.gltrusov.compose.samples.terminal.data.Bar
import kotlin.math.roundToInt

private const val MIN_VISIBLE_BARS_COUNT = 10

@Composable
fun Terminal(bars: List<Bar>) {
    var visibleBarsCount by remember { mutableIntStateOf(50) }

    var barWidth by remember { mutableFloatStateOf(0f) }

    var terminalWidth by remember { mutableFloatStateOf(0f) }

    var scrolledBy by remember { mutableFloatStateOf(0f) }

    val transformableState = TransformableState { zoomChange, panChange, _ ->
        visibleBarsCount = (visibleBarsCount / zoomChange)
            .roundToInt()
            .coerceIn(MIN_VISIBLE_BARS_COUNT, bars.size)

        scrolledBy = (scrolledBy + panChange.x)
            .coerceIn(0f, bars.size * barWidth - terminalWidth)
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .transformable(transformableState),
    ) {
        terminalWidth = size.width
        val max = bars.maxOf { it.highest }
        val min = bars.minOf { it.lowest }
        barWidth = size.width / visibleBarsCount
        val pxPerPoint = size.height / (max - min)

        translate(left = scrolledBy) {// смещает канвас
            bars.forEachIndexed { i, bar ->
                val offsetX = size.width - i * barWidth
                drawLine(
                    color = Color.White,
                    start = Offset(offsetX, size.height - ((bar.lowest - min) * pxPerPoint)),
                    end = Offset(offsetX, size.height - ((bar.highest - min) * pxPerPoint)),
                    strokeWidth = 1f
                )

                val barColor = if (bar.close >= bar.open) Color.Green else Color.Red
                drawLine(
                    color = barColor,
                    start = Offset(offsetX, size.height - ((bar.close - min) * pxPerPoint)),
                    end = Offset(offsetX, size.height - ((bar.open - min) * pxPerPoint)),
                    strokeWidth = (barWidth / 2f).coerceAtLeast(3f)
                )
            }
        }

    }
}