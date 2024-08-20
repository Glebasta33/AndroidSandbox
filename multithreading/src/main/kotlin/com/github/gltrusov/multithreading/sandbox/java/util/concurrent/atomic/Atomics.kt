package com.github.gltrusov.multithreading.sandbox.java.util.concurrent.atomic

import com.github.gltrusov.core.ui.compose.logOnUi
import com.github.gradle_sandbox.Markdown
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

/**
 * Atomic (атомики) позволяют атомарно изменять переменные без блокирования потоков.
 *
 * Простой пример: в счётчике не обеспечена атомарность изменений.
 * Операция increment содержит как минимум 3 этапа: чтение, модификация, вставка.
 * Тк это операция не атомарна, любой поток может вклиниться в промежуточное состояние и считать не актуальные данные.
 * Например, сразу 10 потоков могут считать counter == 0, параллельно выполнить изменение и все вставить значение 1.
 */
private class CounterUnsafe {

    private var counter = 0

    fun increment() {
        counter++
    }

    fun current(): Int = counter
}

/**
 * Чтобы атомрно модифицировать не примитивы, а объекты - используй AtomicRefence + Immutablity TODO
 */
private class AtomicCounterSafe {

    private var counter = AtomicInteger(0)

    fun increment() {
        counter.incrementAndGet()
    }

    fun current(): Int = counter.get()
}

@Markdown("Atomics.md")
fun Atomics() {
    val counterUnsafe = CounterUnsafe()

    (1..100)
        .map {
            thread { repeat(1000) { counterUnsafe.increment() } }
        }
        .forEach { thread -> thread.join()  }

    logOnUi("Counter unsafe result: ${counterUnsafe.current()}") // 92832

    val atomicCounter = AtomicCounterSafe()

    (1..100)
        .map {
            thread { repeat(1000) { atomicCounter.increment() } }
        }
        .forEach { thread -> thread.join()  }

    logOnUi("Counter unsafe result: ${atomicCounter.current()}") // 100000
}

fun main() {
    Atomics()
}