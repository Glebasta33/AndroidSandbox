package com.github.gltrusov.multithreading.coroutines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.gltrusov.navigation.component.CoreFragment
import com.github.gltrusov.ui.theme.AndroidSandboxTheme
import kotlinx.coroutines.launch

/**
 * ## 8 Корутины в Android. Специальные API
 *
 * В Android часто нужно работать с ЖЦ компонентов.
 * Корутины являются рекомендованным подходом к выполнению асинхронных операций в Android.
 * У корутин есть привязка выполнения фоновых операций к ЖЦ (CoroutineScope).
 *
 * ## Специальные CoroutineScope.
 * В большинстве случаев мы запускаем корутину во ViewModel.
 * viewModelScope привязан к ЖЦ ViewModel - см: [CoroutinesInAndroidViewModel].
 * А значит скоуп будет жить при изменении конфигурации, но отменится при уходе с экрана.
 *
 * Также есть скоуп, привязанный к ЖЦ активности (или фрагмента) - lifecycleScope (от onCreate() до onDestroy())
 * А также внутри фрагмента есть скоуп, привязанный к ЖЦ View - viewLifecicleOwner.lifecycleScope (onCreateView() - onDestroyView()).
 * Также lifeCycleScope позволяет запустить корутину в заданный момент ЖЦ.
 * Для этого у Lifecycle есть специальные функции:
 * ```
 * suspend fun <R> Lifecycle.withCreated{...}
 * suspend fun <R> Lifecycle.withStarted{...}
 * suspend fun <R> Lifecycle.withResumed{...}
 * ```
 * LifeData и Flow имеют разный момент отписки, что может приводить к багам при миграции.
 * Чтобы повторить поведение как у LifeData, нужно использовать специальное API - repeatOnLifecycle.
 *
 * Также есть flowWithLifecycle // TODO: Разобраться в работе на отдельном экране.
 *
 * @see [Source] (https://www.youtube.com/watch?v=JvqRUF87z9w&list=PL0SwNXKJbuNmsKQW9mtTSxNn00oJlYOLA&index=9)
 */
class CoroutinesInAndroidFragment : CoreFragment() {

    private val vm by viewModels<CoroutinesInAndroidViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch { // == this.lifecycle.coroutineScope
            vm.flow
                .collect {
                println("Coroutine is running in Fragment LifeCycle: $it")
            }

//            withCreated { println("withCreated") }
//            withResumed { println("withResumed") }

            repeatOnLifecycle(state = Lifecycle.State.RESUMED) {//TODO: Разобраться в чём именно преимущество (см. у Сумина - раздел Flow).
                vm.flow.collect {
                    println("repeatOnLifecycle: $it")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            AndroidSandboxTheme {
                val state = vm.state.collectAsState()
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.value.toString())
                    Button(onClick = { vm.loadData() }) {
                        Text(text = "Load random number")
                    }
                }
            }
        }
    }

}