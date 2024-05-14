package com.github.gltrusov.compose.samples.terminal.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.github.gltrusov.compose.samples.terminal.data.Bar

@Composable
fun Terminal(bars: List<Bar>) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        val max = bars.maxOf { it.highest }
        val min = bars.minOf { it.lowest }
        val barWidth = size.width / bars.size
        val pxPerPoint = size.height / (max - min)
        bars.forEachIndexed { i, bar ->
            drawLine(
                color = Color.White,
                start = Offset(i * barWidth, size.height - ((bar.lowest - min) * pxPerPoint)),
                end = Offset(i * barWidth, size.height - ((bar.highest - min) * pxPerPoint)),
                strokeWidth = 1f
            )
        }
    }
}