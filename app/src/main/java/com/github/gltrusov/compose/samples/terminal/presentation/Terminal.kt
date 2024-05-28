package com.github.gltrusov.compose.samples.terminal.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
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
fun Terminal(
    bars: List<Bar>,
    modifier: Modifier = Modifier
) {
    var state by rememberTerminalState(bars)

    Chart(
        state = state,
        modifier = modifier,
        onTerminalStateChanged = { state = it }
    )

    bars.firstOrNull()?.let {
        Prices(
            max = state.max,
            min = state.min,
            pxPerPoint = state.pxPerPoint,
            lastPrice = it.close,
            modifier = modifier
        )
    }

}

@Composable
private fun Chart(
    state: TerminalState,
    modifier: Modifier = Modifier,
    onTerminalStateChanged: (TerminalState) -> Unit
) {
    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        val visibleBarsCount = (state.visibleBarsCount / zoomChange)
            .roundToInt()
            .coerceIn(MIN_VISIBLE_BARS_COUNT, state.bars.size)

        val scrolledBy = (state.scrolledBy + panChange.x)
            .coerceIn(0f, state.bars.size * state.barWidth - state.terminalWidth)

        onTerminalStateChanged(
            state.copy(
                visibleBarsCount = visibleBarsCount,
                scrolledBy = scrolledBy
            )
        )
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .clipToBounds()
            .padding(vertical = 32.dp)
            .padding(end = 32.dp)
            .transformable(transformableState)
            .onSizeChanged { size ->
                onTerminalStateChanged(
                    state.copy(
                        terminalWidth = size.width.toFloat(),
                        terminalHeight = size.height.toFloat()
                    )
                )
            },
    ) {
        val min = state.min
        val pxPerPoint = state.pxPerPoint

        translate(left = state.scrolledBy) {// смещает канвас
            state.bars.forEachIndexed { i, bar ->
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
    }
}

@Composable
private fun Prices(
    max: Float,
    min: Float,
    pxPerPoint: Float,
    lastPrice: Float,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
            .padding(vertical = 32.dp)
    ) {
        drawPrices(max, min, pxPerPoint, lastPrice, textMeasurer)
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
        topLeft = Offset(size.width - textLayoutResult.size.width - 4.dp.toPx(), offsetY)
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