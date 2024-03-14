package com.github.gltrusov.compose.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gltrusov.navigation.component.CoreFragment

class ComposeCanvasTestFragment : CoreFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            var isFirst by remember { mutableStateOf(true) }
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable { isFirst = !isFirst }) {
                if (isFirst) {
                    CanvasTest1()
                } else {
                    CanvasTest2()
                }
            }

        }
    }

}

@Preview
@Composable
private fun CanvasTest1() {
    /**
     * onDraw - обязательный параметр Canvas. Это лямбда в которой происходит вся отрисовка.
     * onDraw - это extension-функция к DrawScope, у которого есть множество методов для
     * отрисоки (drawLine, drawRect, drawCircle...), а также свойста (size, center, ...).
     */
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        /**
         * У линии должны быть обязательные параметры: color (или brush) + start и end.
         */
        drawLine(
            color = Color.White,
            start = Offset(0f, 0f), // Offset - отступ от левого верхнего края экрана
            end = Offset(size.width, size.height),
            strokeWidth = 5.dp.toPx()
        )
        drawLine(
            color = Color.White,
            start = Offset(size.width, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 5.dp.toPx()
        )
        drawCircle(
            color = Color.White,
            radius = 170.dp.toPx(),
            style = Stroke(width = 5.dp.toPx())
        )
        drawLego()
    }
}

private fun DrawScope.drawLego() {
    // Л
    drawLine(
        color = Color.White,
        start = Offset(center.x - 120.dp.toPx(), center.y + 10.dp.toPx()),
        end = Offset(center.x - 90.dp.toPx(), center.y - 50.dp.toPx()),
        strokeWidth = 5.dp.toPx()
    )
    drawLine(
        color = Color.White,
        start = Offset(center.x - 60.dp.toPx(), center.y + 10.dp.toPx()),
        end = Offset(center.x - 90.dp.toPx(), center.y - 50.dp.toPx()),
        strokeWidth = 5.dp.toPx()
    )

    // E
    drawLine(
        color = Color.White,
        start = Offset(center.x - 20.dp.toPx(), center.y - 140.dp.toPx()),
        end = Offset(center.x - 20.dp.toPx(), center.y - 80.dp.toPx()),
        strokeWidth = 5.dp.toPx()
    )
    drawLine(
        color = Color.White,
        start = Offset(center.x - 20.dp.toPx(), center.y - 140.dp.toPx()),
        end = Offset(center.x + 10.dp.toPx(), center.y - 140.dp.toPx()),
        strokeWidth = 5.dp.toPx()
    )
    drawLine(
        color = Color.White,
        start = Offset(center.x - 20.dp.toPx(), center.y - 110.dp.toPx()),
        end = Offset(center.x + 10.dp.toPx(), center.y - 110.dp.toPx()),
        strokeWidth = 5.dp.toPx()
    )
    drawLine(
        color = Color.White,
        start = Offset(center.x - 20.dp.toPx(), center.y - 80.dp.toPx()),
        end = Offset(center.x + 10.dp.toPx(), center.y - 80.dp.toPx()),
        strokeWidth = 5.dp.toPx()
    )

    // Г
    drawLine(
        color = Color.White,
        start = Offset(center.x + 80.dp.toPx(), center.y + 10.dp.toPx()),
        end = Offset(center.x + 80.dp.toPx(), center.y - 50.dp.toPx()),
        strokeWidth = 5.dp.toPx()
    )
    drawLine(
        color = Color.White,
        start = Offset(center.x + 80.dp.toPx(), center.y - 50.dp.toPx()),
        end = Offset(center.x + 110.dp.toPx(), center.y - 50.dp.toPx()),
        strokeWidth = 5.dp.toPx()
    )

    // O
    drawCircle(
        color = Color.White,
        radius = 30.dp.toPx(),
        style = Stroke(width = 5.dp.toPx()),
        center = center.copy(y = center.y + 110.dp.toPx())
    )
}

@Preview
@Composable
private fun CanvasTest2() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(
                /**
                 * Brush позволяет задавать градиенты
                 */
                brush = Brush.linearGradient(
                    colors = listOf(Color.Cyan, Color.Magenta)
                )
            )
    ) {
        /**
         * Path позволяет рисовать сложные фигуры.
         */
        drawPath(
            path = Path().apply {
                moveTo(center.x, 100.dp.toPx()) // установить координаты для курсора
                lineTo(center.x + 25.dp.toPx(), 150.dp.toPx())
                lineTo(center.x + 75.dp.toPx(), 150.dp.toPx())
                lineTo(center.x + 35.dp.toPx(), 185.dp.toPx())
                lineTo(center.x + 50.dp.toPx(), 230.dp.toPx())
                lineTo(center.x, 205.dp.toPx())

                lineTo(center.x - 50.dp.toPx(), 230.dp.toPx())
                lineTo(center.x - 35.dp.toPx(), 185.dp.toPx())
                lineTo(center.x - 75.dp.toPx(), 150.dp.toPx())
                lineTo(center.x - 25.dp.toPx(), 150.dp.toPx())
                lineTo(center.x, 100.dp.toPx())
            },
            style = Fill,
            brush = Brush.linearGradient(
                colors = listOf(Color.Cyan, Color.Magenta),
                start = Offset(0f, 0f),
                end = Offset(0f, 20.dp.toPx()),
                tileMode = TileMode.Mirror
            )
        )

        House()
    }
}

private fun DrawScope.House() {
    drawPath(
        path = Path().apply {
            moveTo(center.x, 300.dp.toPx())
            lineTo(center.x + 100.dp.toPx(), 400.dp.toPx())
            lineTo(center.x - 100.dp.toPx(), 400.dp.toPx())
            lineTo(center.x, 300.dp.toPx())
            moveTo(center.x + 100.dp.toPx(), 400.dp.toPx())
            lineTo(center.x + 100.dp.toPx(), 550.dp.toPx())
            lineTo(center.x - 100.dp.toPx(), 550.dp.toPx())
            lineTo(center.x - 100.dp.toPx(), 400.dp.toPx())
            moveTo(center.x - 50.dp.toPx(), 450.dp.toPx())
            lineTo(center.x - 50.dp.toPx(), 500.dp.toPx())
            lineTo(center.x + 50.dp.toPx(), 500.dp.toPx())
            lineTo(center.x + 50.dp.toPx(), 450.dp.toPx())
            lineTo(center.x - 50.dp.toPx(), 450.dp.toPx())
            moveTo(center.x - 50.dp.toPx(), 475.dp.toPx())
            lineTo(center.x + 50.dp.toPx(), 475.dp.toPx())
            moveTo(center.x, 450.dp.toPx())
            lineTo(center.x, 500.dp.toPx())
        },
        style = Stroke(width = 4.dp.toPx()),
        color = Color.White
    )
}
