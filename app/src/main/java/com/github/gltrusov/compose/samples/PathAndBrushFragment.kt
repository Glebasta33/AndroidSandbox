package com.github.gltrusov.compose.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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

class PathAndBrushFragment : CoreFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            PathAndBrush()
        }
    }
}


@Preview
@Composable
private fun PathAndBrush() {
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
