package com.github.gltrusov.views.presentation.custom_view.habit_tracker

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.views.R
import java.time.LocalDate

internal class HabitTrackerActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_tracker)

        val habitTrackerView = findViewById<HabitTrackerView>(R.id.habit_view)

        habitTrackerView.setItems(habits)

//        findViewById<MarkdownView>(R.id.markdown).loadMarkdownFile("file:///android_asset/basic_markdown_view.md")

    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private val habits = listOf(
            HabitItem(
                "Зарядка",
                Color.GREEN,
                listOf(
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(6),
                        isDone = true
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(5),
                        isDone = true
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(4),
                        isDone = true
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(3),
                        isDone = true
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(2),
                        isDone = true
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(1),
                        isDone = true
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now(),
                        isDone = false
                    )
                )
            ),
            HabitItem(
                "Медитация",
                Color.CYAN,
                listOf(
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(6),
                        isDone = false
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(5),
                        isDone = true
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(4),
                        isDone = false
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(3),
                        isDone = true
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(2),
                        isDone = true
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now().minusDays(1),
                        isDone = true
                    ),
                    HabitItem.Stat(
                        date = LocalDate.now(),
                        isDone = false
                    )
                )
            )
        )
    }
}