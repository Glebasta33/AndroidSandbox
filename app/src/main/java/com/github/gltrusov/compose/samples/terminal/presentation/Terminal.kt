package com.github.gltrusov.compose.samples.terminal.presentation

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.roundToInt

private const val MIN_VISIBLE_BARS_COUNT = 10

@Composable
fun Terminal(
    modifier: Modifier = Modifier
) {
    val viewModel: TerminalViewModel = viewModel()
    val screenState = viewModel.state.collectAsState()

    when (val currentState = screenState.value) {
        is TerminalScreenState.Content -> {
            val state = rememberTerminalState(currentState.bars)

            Chart(
                state = state,
                modifier = modifier,
                onTerminalStateChanged = { state.value = it }
            )

            currentState.bars.firstOrNull()?.let {
                Prices(
                    state = state,
                    lastPrice = it.close,
                    modifier = modifier
                )
            }

            Timeframes(
                selectedFrame = currentState.timeframe,
                onTimeframeSelected = { selectedTimeframe ->
                    viewModel.loadBarList(selectedTimeframe)
                }
            )
        }

        is TerminalScreenState.Initial -> {
            Log.d("MyTest", "Initial")
        }

        is TerminalScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun Timeframes(
    selectedFrame: Timeframe,
    onTimeframeSelected: (Timeframe) -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 16.dp, top = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Timeframe.entries.forEach { timeframe ->
            val isSelected = selectedFrame == timeframe
            AssistChip(
                onClick = { onTimeframeSelected.invoke(timeframe) },
                label = { Text(text = timeframe.label) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (isSelected) Color.White else Color.Black,
                    labelColor = if (isSelected) Color.Black else Color.White
                )
            )
        }
    }
}

@Composable
private fun Chart(
    state: State<TerminalState>,
    modifier: Modifier = Modifier,
    onTerminalStateChanged: (TerminalState) -> Unit
) {
    val currentState = state.value
    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        val visibleBarsCount = (currentState.visibleBarsCount / zoomChange)
            .roundToInt()
            .coerceIn(MIN_VISIBLE_BARS_COUNT, currentState.bars.size)

        val scrolledBy = (currentState.scrolledBy + panChange.x)
            .coerceIn(
                0f,
                currentState.bars.size * currentState.barWidth - currentState.terminalWidth
            )

        onTerminalStateChanged(
            currentState.copy(
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
                    currentState.copy(
                        terminalWidth = size.width.toFloat(),
                        terminalHeight = size.height.toFloat()
                    )
                )
            },
    ) {
        val min = currentState.min
        val pxPerPoint = currentState.pxPerPoint

        translate(left = currentState.scrolledBy) {// смещает канвас
            currentState.bars.forEachIndexed { i, bar ->
                val offsetX = size.width - i * currentState.barWidth
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
                    strokeWidth = (currentState.barWidth / 2f).coerceAtLeast(3f)
                )
            }
        }
    }
}

@Composable
private fun Prices(
    state: State<TerminalState>,
    lastPrice: Float,
    modifier: Modifier = Modifier
) {
    val currentState = state.value
    val textMeasurer = rememberTextMeasurer()

    val max: Float = currentState.max
    val min: Float = currentState.min
    val pxPerPoint: Float = currentState.pxPerPoint

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