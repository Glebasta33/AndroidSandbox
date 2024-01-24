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
import com.github.gltrusov.dagger.di_basics.Computer
import com.github.gltrusov.dagger.di_basics.appComponent
import com.github.gltrusov.ui.theme.AndroidSandboxTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    /**
     * Так как через в параметре функции inject() в компоненте указывается класс, в который нужно внедрить зависимость,
     * у Dagger есть доступ к данному классу и он способен принициализировать lateinit свойства.
     */
    @Inject
    lateinit var injectedComputer: Computer

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * Вызов метода компонента производит иньекцию необходимых зависимостей из графа.
         */
        appComponent.inject(this)

        /**
         * Из графа можно получить любой объект, если он поставляется в модулях компонента и определён в интерфейсе компонента.
         */
        val computer: Computer = appComponent.computer

        super.onCreate(savedInstanceState)
        setContent {
            AndroidSandboxTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Computer: $injectedComputer")
                    }
                }
            }
        }
    }
}