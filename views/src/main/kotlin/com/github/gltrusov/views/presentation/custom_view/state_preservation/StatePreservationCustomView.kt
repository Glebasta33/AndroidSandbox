package com.github.gltrusov.views.presentation.custom_view.state_preservation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.github.gradle_sandbox.Markdown

@Markdown("state_preservation_view.md")
internal class StatePreservationCustomView(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private var counter = 0
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 32f
    }

    private val circlePaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }
    private val rectPaint = Paint().apply { color = Color.BLUE }
    private val arcPaint = Paint().apply { color = Color.GREEN }
    private val rectF = RectF()
    private val arcRectF = RectF()

    private var centerX = 0f
    private var centerY = 0f

    /**
     * onSizeChanged вызывается при изменении размеров (смена ориентации, изменение размера родителя).
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF.left = 20f
        rectF.top = 40f
        rectF.right = w - 20f
        rectF.bottom = h - 40f

        arcRectF.left = 20f
        arcRectF.top = 40f
        arcRectF.right = w - 20f
        arcRectF.bottom = h - 40f

        centerX = w / 2f
        centerY = h / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(rectF, rectPaint)
        canvas.drawCircle(centerX, centerY, 100f, circlePaint)
        canvas.drawArc(arcRectF, 180f, 90f, true, arcPaint)
        canvas.drawText(counter.toString(), width / 2f, height / 2f, textPaint)

        counter++
        invalidate()
    }

    /**
     * Методы onSaveInstanceState() и onRestoreInstanceState() позволяют сохронить
     * состояние View (например, при перевороте экрана).
     * onSaveInstanceState вызывается перед уничтожением View.
     * Данные нужно упаковать в Bundle.
     */
    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState()
        val bundle = Bundle()
        bundle.putInt("counter", counter)
        // при сохранении стейта также нужно обработать сохранения стейта родительской вьюхи вручную
        bundle.putParcelable("instantState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val bundle = state as Bundle
        counter = bundle.getInt("counter")
        super.onRestoreInstanceState(bundle.getParcelable("instantState"))
    }
}