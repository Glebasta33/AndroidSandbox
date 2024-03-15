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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
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
            CanvasTest()
        }
    }

}

@Preview
@Composable
private fun CanvasTest() {
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
