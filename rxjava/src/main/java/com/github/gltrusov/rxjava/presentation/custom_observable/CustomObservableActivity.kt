package com.github.gltrusov.rxjava.presentation.custom_observable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rxjava3.subscribeAsState
import com.github.gradle_sandbox.Markdown
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Locale

/**
 * RxJava - библиотека для асинхронного программирования,
 * которая работает на основе реактивных потоков
 * (последовательность данных, на которую можно подписаться и реагировать).
 *
 * RxJava основана на паттерне "наблюдатель".
 * Основные сущности:
 * - Observable - источник данных.
 * - Subscriber - потребитель данных.
 *
 * Observable эмитит данные и завершает работу (успешно или с ошибкой).
 * Для каждого Subscriber, подписанного на Observable,
 * вызывается метод Subscriber.onNext() для каждого элемента
 * потока данных, после которого может быть вызван
 * как Subscriber.onComplete(), так и Subscriber.onError().
 */
@Markdown("custom_observable.md")
class CustomObservableActivity : ComponentActivity() {

    private val disposables = CompositeDisposable()
    private val simpleObservable = Observable.create<Any> { emmiter ->
        repeat(5) { i ->
            emmiter.onNext("Hello, RxJava $i!")
            Thread.sleep(1000)
            emmiter.onNext(i)
            Thread.sleep(1000)
        }

        emmiter.onComplete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = simpleObservable.subscribeAsState(initial = "Initial")
            CustomObservableScreen(state.value.toString())
        }

        val disposable = simpleObservable
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it !is Number }
            .map { it.toString() }
            .map { it.uppercase(Locale.ROOT) }
            .subscribe(
                { string ->
                    toast("onNext: $string")
                },
                { error ->
                    toast("onError: ${error.message}")
                },
                {
                    toast("onComplete")
                }
            )

        disposables.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        fun start(context: Context) {
            Intent(context, CustomObservableActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this@CustomObservableActivity, text, Toast.LENGTH_SHORT).show()
    }
}