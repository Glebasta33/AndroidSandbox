package com.github.gltrusov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.gltrusov.ui.theme.AndroidSandboxTheme

/**
 * ## Navigation.
 * Навигаяция в Android состоит из 3-х ключевых концепций:
 * - Host - UI элемент, который содержит текущую точку назначения навигации (destination).
 * В процессе навигации destinations меняются внутри Host.
 * - Graph - структура данных определяющая места назначения и их связь между собой.
 * - Controller - центральный координатор, управляющий навигацией между точками назначения.
 * Имеет методы для навигации, обработки диплинков, управления back stack`ом и т.д.
 *
 * ! Эти 3 концепции используются как при навигации в View, так и в Compose.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            AndroidSandboxTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Computer: ")
                    }
                }
            }
        }
    }
}