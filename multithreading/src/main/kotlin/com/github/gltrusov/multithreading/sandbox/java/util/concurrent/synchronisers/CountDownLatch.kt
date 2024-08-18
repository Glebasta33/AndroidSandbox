package com.github.gltrusov.multithreading.sandbox.java.util.concurrent.synchronisers

import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * CountDownLatch позволяет синхронизировать (приостановить) набор потоков
 * и дать им в определённое время сигнал на дальнейшее выполнение.
 */
fun main() {
    val latch = CountDownLatch(3)

    repeat(3) {
        thread {
            println("${Thread.currentThread().name} is stared")
            Thread.sleep(Random.nextLong(0, 5) * 1000) // имитация длительной работы
            latch.countDown() // именьшает счётчик на 1
        }
    }

    latch.await() // ожидает обнуления счётчика
    println("${Thread.currentThread().name} дождался выполнения фоновых потоков и продолжил своё выполнение.")
}