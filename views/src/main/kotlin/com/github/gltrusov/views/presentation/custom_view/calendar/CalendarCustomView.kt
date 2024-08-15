package com.github.gltrusov.views.presentation.custom_view.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.github.gltrusov.views.R
import com.github.gradle_sandbox.Markdown
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Markdown("calendar_markdown_view.md")
internal class CalendarCustomView(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    // region Paint ================================================================================

    private val separatorsPaint = Paint().apply {
        strokeWidth = resources.getDimension(R.dimen.gant_separator_width)
        color = ContextCompat.getColor(context, R.color.grey_300)
    }

    private val hourNamePaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = resources.getDimension(R.dimen.gant_period_name_text_size)
        color = ContextCompat.getColor(context, R.color.grey_500)
    }

    private val eventPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.blue_700)
    }

    private val eventNamePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = resources.getDimension(R.dimen.gant_task_name_text_size)
        color = Color.WHITE
    }

    private val backgroundPaint = Paint().apply {
        color = Color.DKGRAY
    }
    // endregion  ==================================================================================

    private val hourHeight = resources.getDimensionPixelSize(R.dimen.calendar_hour_height)

    private val eventCornerRadius = resources.getDimension(R.dimen.gant_task_corner_radius)

    // Цвета градиента
    private val gradientStartColor = ContextCompat.getColor(context, R.color.blue_700)
    private val gradientEndColor = ContextCompat.getColor(context, R.color.blue_200)

    private val contentHeight: Int
        get() = hourHeight * visibleHours.size

    private val now = LocalDateTime.now().withMinute(0).withSecond(0)

    private val visibleHours = initHours()

    private var events: List<Event> = emptyList()
    private var uiEvents: List<UiEvents> = emptyList()

    fun setEvents(events: List<Event>) {
        if (events != this.events) {
            this.events = events
            uiEvents = events.map(::UiEvents)

            requestLayout()
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        val height = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> contentHeight
            MeasureSpec.EXACTLY -> heightSpecSize
            MeasureSpec.AT_MOST -> contentHeight.coerceAtMost(heightSpecSize)
            else -> error("Unreachable")
        }

        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val width = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.UNSPECIFIED -> 200//TODO Добавить значение для wrap_content (minContentWidth)
            MeasureSpec.EXACTLY -> widthSpecSize
            MeasureSpec.AT_MOST -> 200.coerceAtMost(heightSpecSize)
            else -> error("Unreachable")
        }

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        eventPaint.shader = LinearGradient(
            0f,
            0f,
            w.toFloat(),
            0f,
            gradientStartColor,
            gradientEndColor,
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) = with(canvas) {
        drawBackground()
        drawHours()
        drawEvents()
    }

    private fun Canvas.drawBackground() {
        drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
    }

    private fun Canvas.drawHours() {
        val textX = 20f
        visibleHours.forEachIndexed { index, text ->
            val textY = hourNamePaint.getTextBaselineByCenter(hourHeight * index.toFloat())
            drawText(text, textX, textY, eventNamePaint)

            val textSizeX = eventNamePaint.measureText(text)

            val separatorY = hourHeight * index.toFloat()
            val separatorX = textX + textSizeX + 20f
            drawLine(separatorX, separatorY, width.toFloat() - 20f, separatorY, separatorsPaint)
        }
    }

    private fun Canvas.drawEvents() {
        uiEvents.forEachIndexed { i, uiEvent ->
            uiEvent.updateRect()
            val eventRect = uiEvent.rect
            val eventTitle = uiEvent.event.title

            drawRoundRect(eventRect, eventCornerRadius, eventCornerRadius, eventPaint)

            drawText(eventTitle, eventRect.left + 20f, eventRect.centerY(), eventNamePaint)
        }
    }

    // ascent - верхняя граница текста, descent - нижняя
    private fun Paint.getTextBaselineByCenter(center: Float) = center - (descent() + ascent()) / 2

    private fun initHours(): List<String> {
        val startTime = now.minusHours(1)
        val endTime = now.plusHours(8)
        var lastTime = startTime
        return mutableListOf<String>().apply {
            while (lastTime <= endTime) {
                add(lastTime.format(formater))
                lastTime = lastTime.plusHours(1)
            }
        }
    }

    private inner class UiEvents(val event: Event) {
        val rect = RectF()

        val isRectOnScreen: Boolean
            get() = rect.top < height && rect.bottom > 0

        private val text = visibleHours.first()
        private val textSizeX = eventNamePaint.measureText(text)
        private val eventX = textSizeX + 60f
        private val startTime = now.minusHours(1).hour

        fun updateRect() {
            fun getY(time: LocalDateTime): Float {
                var y = (time.hour.toFloat() - startTime) * hourHeight
                if (time.minute != 0) {
                    y += (time.minute.toFloat() / 60) * hourHeight
                }
                return y
            }

            rect.set(
                eventX,
                getY(event.dateStart) ?: eventCornerRadius, //TODO?
                width - 20f,
                getY(event.dateEnd) ?: (height + eventCornerRadius), //TODO?
            )
        }
    }

    companion object {
        private const val HOUR_PATTERN = "HH:mm"
        private val formater = DateTimeFormatter.ofPattern(HOUR_PATTERN)
    }
}

data class Event(
    val title: String,
    val dateStart: LocalDateTime,
    val dateEnd: LocalDateTime
)