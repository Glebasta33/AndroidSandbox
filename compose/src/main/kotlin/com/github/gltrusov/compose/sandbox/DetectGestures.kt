package com.github.gltrusov.compose.sandbox

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun DetectGestures() {
    var circleCenters by rememberSaveable { mutableStateOf<List<Point>>(emptyList()) }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(key1 = Unit) {
                detectDragGestures(
                    onDragStart = {
                        circleCenters += Point(offset = it, isStartedPosition = true)
                    },
                    onDrag = { change, _ ->
                        circleCenters += change.historical.map {
                            Point(offset = it.position, isStartedPosition = false)
                        }
                    }
                )
            }
    ) {
        val path = Path().apply {
            circleCenters.forEach { point ->
                if (point.isStartedPosition) {
                    moveTo(point.offset.x, point.offset.y)
                    drawStartEndCircle(point.offset)
                } else {
                    lineTo(point.offset.x, point.offset.y)
                }
            }
        }
        drawPath(
            path = path,
            brush = Brush.linearGradient(listOf(Color.Magenta, Color.Blue, Color.Red)),
            style = Stroke(width = 6.dp.toPx())
        )
    }
}

private data class Point(
    val offset: Offset,
    val isStartedPosition: Boolean
)

private fun DrawScope.drawStartEndCircle(offset: Offset) {
    drawCircle(
        radius = 3.dp.toPx(),
        center = offset,
        brush = Brush.linearGradient(listOf(Color.Magenta, Color.Blue, Color.Red))
    )
}