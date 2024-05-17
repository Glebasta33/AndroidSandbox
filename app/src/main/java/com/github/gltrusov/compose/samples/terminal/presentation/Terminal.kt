package com.github.gltrusov.compose.samples.terminal.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.gltrusov.compose.samples.terminal.data.Bar
import kotlin.math.roundToInt

private const val MIN_VISIBLE_BARS_COUNT = 10

@Composable
fun Terminal(bars: List<Bar>) {
    var state by rememberTerminalState(bars)

    val transformableState = TransformableState { zoomChange, panChange, _ ->
        val visibleBarsCount = (state.visibleBarsCount / zoomChange)
            .roundToInt()
            .coerceIn(MIN_VISIBLE_BARS_COUNT, bars.size)

        val scrolledBy = (state.scrolledBy + panChange.x)
            .coerceIn(0f, bars.size * state.barWidth - state.terminalWidth)

        state = state.copy(
            visibleBarsCount = visibleBarsCount,
            scrolledBy = scrolledBy
        )
    }

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(vertical = 32.dp)
            .transformable(transformableState)
            .onSizeChanged { size ->
                state = state.copy(terminalWidth = size.width.toFloat())
            },
    ) {
        val max = state.visibleBars.maxOf { it.highest }
        val min = state.visibleBars.minOf { it.lowest }
        val pxPerPoint = size.height / (max - min)

        translate(left = state.scrolledBy) {// смещает канвас
            bars.forEachIndexed { i, bar ->
                val offsetX = size.width - i * state.barWidth
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
                    strokeWidth = (state.barWidth / 2f).coerceAtLeast(3f)
                )
            }
        }
        bars.firstOrNull()?.let {
            drawPrices(
                max = max,
                min = min,
                pxPerPoint = pxPerPoint,
                lastPrice = it.close,
                textMeasurer = textMeasurer
            )
        }
    }
}

private fun DrawScope.drawPrices(
    max: Float,
    min: Float,
    pxPerPoint: Float,
    lastPrice: Float,
    textMeasurer: TextMeasurer
) {
    // max
    drawDashedLine(
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f),
    )
    drawTextPrice(
        textMeasurer = textMeasurer,
        price = max,
        offsetY = 0f
    )

    // last price
    val lastPriceOffsetY = size.height - ((lastPrice - min) * pxPerPoint)
    drawDashedLine(
        start = Offset(0f, lastPriceOffsetY),
        end = Offset(size.width, lastPriceOffsetY),
    )
    drawTextPrice(
        textMeasurer = textMeasurer,
        price = max,
        offsetY = lastPriceOffsetY
    )

    // min
    drawDashedLine(
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height),
    )
    drawTextPrice(
        textMeasurer = textMeasurer,
        price = min,
        offsetY = size.height
    )
}

private fun DrawScope.drawTextPrice(
    textMeasurer: TextMeasurer,
    price: Float,
    offsetY: Float
) {
    val textLayoutResult = textMeasurer.measure(
        text = price.toString(),
        style = TextStyle(
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 12.sp
        )
    )
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(size.width - textLayoutResult.size.width, offsetY)
    )
}

private fun DrawScope.drawDashedLine(
    color: Color = Color.White.copy(alpha = 0.5f),
    start: Offset,
    end: Offset,
    strokeWidth: Float = 2f
) {
    drawLine(
        color = color,
        start = start,
        end = end,
        strokeWidth = strokeWidth,
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(
                4.dp.toPx(), 4.dp.toPx()
            )
        )
    )
}