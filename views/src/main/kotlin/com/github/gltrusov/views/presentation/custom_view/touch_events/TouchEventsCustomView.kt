package com.github.gltrusov.views.presentation.custom_view.touch_events

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.github.gradle_sandbox.Markdown

@Markdown("touch_events_custom_view.md")
internal class TouchEventsCustomView(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private val bgPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }


    private val rectF = RectF().apply {
        top = 80f
        left = 220f
        right = 740f
        bottom = 500f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(rectF, bgPaint)
    }

    /**
     * onTouchEvent - метод для обработки касаний на View.
     * Возвращаемое значение: true - событие было обработано,
     * false - событие продолжит путь по иерархии View.
     *
     * 3 основных события:
     * ACTION_DOWN — когда палец нажимается на экран
     * ACTION_MOVE — когда палец перемещается по экрану
     * ACTION_UP — когда палец отпускается от экрана.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // обработка нажатия пальца на экран
                bgPaint.color = Color.BLUE
                rectF.apply {
                    top = 180f
                    left = 320f
                    right = 540f
                    bottom = 400f
                }
                invalidate() // вызывает onDraw()
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                // обработка перемещения пальца по экрану
                return true
            }

            MotionEvent.ACTION_UP -> {
                // обработка отпускания пальца от экрана
                bgPaint.color = Color.RED
                rectF.apply {
                    top = 80f
                    left = 220f
                    right = 740f
                    bottom = 500f
                }
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}