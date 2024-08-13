package com.github.gltrusov.views.presentation.custom_view.on_measure

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.github.gradle_sandbox.Markdown
import kotlin.math.min

@Markdown("measured_markdown_view.md")
internal class MeasuredCustomView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val bgPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    /**
     * MeasureSpec - класс, используемый для определения размеров View.
     * MeasureSpec состоит из 2-х основных компонентов: размера и режима измерения.
     * Режим измерения может быть 3-х типов:
     * - EXACTLY - точный фиксированный размер, заданный атрибутом android:layout_with (height).
     * - AT_MOST - (не больше) размер может быть любым, но не превышать родителя (wrap_content, math_parent).
     * - UNSPECIFIED - размер может быть любым, не ограничен размером родителя.
     * MeasureSpec задаётся отдельно для высоты и ширины.
     * getSize() - получение размера, getMode() - получение режима.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = 100
        val desiredHeight = 100

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when(widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        val height = when(heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> desiredHeight
        }

        // при переопределении onMeasure необходимо указать размер, используя setMeasuredDimension
        setMeasuredDimension(width, height)
    }

    /**
     * onLayout вызывается при каждом изменении размера и позиции View.
     * Обычно нужно переопределять, когда в CustomView есть дочерние View,
     * которые нужно разместить в определённом порядке.
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width / 2f, height / 2f, /*radius*/200f, bgPaint)
    }
}