package com.github.gltrusov.views.presentation.custom_view.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.Shader
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
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
        color = ContextCompat.getColor(context, R.color.grey_500)
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

    private val timelinePaint = Paint().apply {
        strokeWidth = resources.getDimension(R.dimen.calendar_timeline_height)
        color = ContextCompat.getColor(context, R.color.orange_700)
        style = Paint.Style.FILL
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
    private val timeline = LocalDateTime.now()


    // Значения последнего эвента
    private val lastPoint = PointF()
    private var lastPointerId = 0

    // Отвечает за зум и сдвиги
    private val transformations = Transformations()

    // Обнаружение и рассчёт скейла
    private val scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())


    private val visibleHours = initHours()

    private var events: List<Event> = emptyList()
    private var uiEvents: List<UiEvents> = emptyList()

    fun setEvents(events: List<Event>) {
        if (events != this.events) {
            this.events = events
            uiEvents = events.map(::UiEvents)
            updateEventsRects()

            requestLayout()
            invalidate()
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        return SavedState(super.onSaveInstanceState()).also(transformations::onSaveState)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            transformations.onRestoreState(state)
        } else {
            super.onRestoreInstanceState(state)
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
        updateEventsRects()
    }

    private fun updateEventsRects() {
        uiEvents.forEach { uiEvent -> uiEvent.updateInitialRect() }
        transformations.recalculate()
    }

    override fun onDraw(canvas: Canvas) = with(canvas) {
        drawBackground()
        drawHours()
        drawEvents()
        drawTimeline()
    }

    private fun Canvas.drawBackground() {
        drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
    }

    private fun Canvas.drawHours() {
        val textX = 20f
        visibleHours.forEachIndexed { index, text ->
            val textY =
                hourNamePaint.getTextBaselineByCenter(hourHeight * index.toFloat() * transformations.scaleY + transformations.translationY)
            drawText(text, textX, textY, eventNamePaint)

            val textSizeX = eventNamePaint.measureText(text)

            val separatorY =
                hourHeight * index.toFloat() * transformations.scaleY + transformations.translationY
            val separatorX = textX + textSizeX + 20f
            drawLine(separatorX, separatorY, width.toFloat() - 20f, separatorY, separatorsPaint)
        }
    }

    private fun Canvas.drawEvents() {
        uiEvents.forEachIndexed { i, uiEvent ->
            if (uiEvent.isRectOnScreen) {
                uiEvent.updateInitialRect()
                val eventRect = uiEvent.rect
                val eventTitle = uiEvent.event.title

                drawPath(uiEvent.path, eventPaint)

                drawText(
                    eventTitle,
                    eventRect.left + 20f,
                    eventRect.centerY() * transformations.scaleY + transformations.translationY,
                    eventNamePaint
                )
            }
        }
    }

    private fun Canvas.drawTimeline() {
        val text = visibleHours.first()
        val textSizeX = eventNamePaint.measureText(text)
        val startX = textSizeX + 40f
        val startTime = timeline.minusHours(1).hour
        var y = (timeline.hour.toFloat() - startTime) * hourHeight

        if (timeline.minute != 0) {
            y += (timeline.minute.toFloat() / 60) * hourHeight
        }
        y = y * transformations.scaleY + transformations.translationY
        drawLine(startX, y, width.toFloat() - 20f, y, timelinePaint)
        drawCircle(startX, y, 12f, timelinePaint)
    }

    // ascent - верхняя граница текста, descent - нижняя
    private fun Paint.getTextBaselineByCenter(center: Float) = center - (descent() + ascent()) / 2

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        return if (event.pointerCount > 1) scaleGestureDetector.onTouchEvent(event) else processMove(
            event
        )
    }

    private fun processMove(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastPoint.set(event.x, event.y)
                lastPointerId = event.getPointerId(0)
                true
            }

            MotionEvent.ACTION_MOVE -> {
                // Если размер контента меньше размера View - сдвиг недоступен
                if (height < contentHeight) {
                    val pointerId = event.getPointerId(0)
                    // Чтобы избежать скачков - сдвигаем, только если поинтер(палец) тот же, что и раньше
                    if (lastPointerId == pointerId) {
                        transformations.addTranslation(event.y - lastPoint.y)
                    }

                    // Запоминаем поинтер и последнюю точку в любом случае
                    lastPoint.set(event.x, event.y)
                    lastPointerId = event.getPointerId(0)
                    true
                } else {
                    false
                }
            }

            else -> false
        }
    }

    private fun initHours(): List<String> {
        val startTime = now.minusHours(1)
        val endTime = now.plusHours(16)
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

        val path = Path()

        val isRectOnScreen: Boolean
            get() = rect.top < height /*&& rect.bottom > 0f*/

        private val untransformedRect = RectF()

        private val text = visibleHours.first()
        private val textSizeX = eventNamePaint.measureText(text)
        private val eventX = textSizeX + 60f
        private val startTime = now.minusHours(1).hour

        fun updateInitialRect() {
            fun getY(time: LocalDateTime): Float {
                var y = (time.hour.toFloat() - startTime) * hourHeight
                if (time.minute != 0) {
                    y += (time.minute.toFloat() / 60) * hourHeight
                }
                return y
            }

            untransformedRect.set(
                eventX,
                getY(event.dateStart) ?: eventCornerRadius, //TODO?
                width - 20f,
                getY(event.dateEnd) ?: (height + eventCornerRadius), //TODO?
            )
            rect.set(untransformedRect)
        }

        fun transform(matrix: Matrix) {
            matrix.mapRect(rect, untransformedRect)
            updatePath()
        }

        private fun updatePath() {
            if (isRectOnScreen) {
                with(path) {
                    reset()
                    addRoundRect(rect, eventCornerRadius, eventCornerRadius, Path.Direction.CW)
                }
            }
        }
    }

    private inner class Transformations {
        var translationY = 0f
            private set
        var scaleY = 1f
            private set

        private val matrix = Matrix()

        // На сколько максимально можно сдвинуть календарь
        private val minTranslation: Float
            get() = (height - contentHeight).toFloat().coerceAtMost(0f)

        // Относительный сдвиг на dy
        fun addTranslation(dy: Float) {
            translationY = (translationY + dy).coerceIn(minTranslation, 0f)
            transformEvents()
        }

        fun addScale(sy: Float) {
            scaleY = (scaleY * sy).coerceIn(0.5f, MAX_SCALE)
            recalculateTranslationY()
            transformEvents()
        }

        fun onSaveState(state: SavedState) {
            state.translationY = translationY
            state.scaleY = scaleY
        }

        fun onRestoreState(state: SavedState) {
            translationY = state.translationY
            scaleY = state.scaleY
            recalculate()
        }

        // Пересчет текущих значений
        fun recalculate() {
            recalculateTranslationY()
            transformEvents()
        }

        // Когда изменился размер View надо пересчитать сдвиг
        private fun recalculateTranslationY() {
            translationY = translationY.coerceIn(minTranslation, 0f)
        }

        private fun transformEvents() {
            with(matrix) {
                reset()
                setScale(1f, scaleY)
                postTranslate(0f, translationY)
                uiEvents.forEach { it.transform(matrix) }
                invalidate()
            }
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            transformations.addScale(detector.scaleFactor)
            return true
        }
    }

    private class SavedState : BaseSavedState {
        var translationY: Float = 0f
        var scaleY: Float = 0f

        // Конструктор для сохранения стейта
        constructor(superState: Parcelable?) : super(superState)

        // Конструктор для восстановления стейта
        constructor(source: Parcel?) : super(source) {
            source?.apply {
                translationY = readFloat()
                scaleY = readFloat()
            }
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeFloat(translationY)
            out.writeFloat(scaleY)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel?): SavedState = SavedState(source)

                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }

    companion object {
        private const val MAX_SCALE = 3f
        private const val HOUR_PATTERN = "HH:mm"
        private val formater = DateTimeFormatter.ofPattern(HOUR_PATTERN)
    }
}

data class Event(
    val title: String,
    val dateStart: LocalDateTime,
    val dateEnd: LocalDateTime
)