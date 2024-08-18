package com.github.gltrusov.multithreading.sandbox.java.util.concurrent.locks

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

/**
 * Lock позволяет более явно и гибко реализовать синхронизацию между потоками, чем synchronized.
 */
fun main() {
    var a = 0
    var b = 0
    val lock: Lock = ReentrantLock()

    val thread0 = thread {
        lock.lock() // начало критической секции, после которой замок (монитор) будет занят и другие потоки заблокируются.
        a = 1
        lock.unlock() // конец критической секции. Замок освобождается.
    }

    val thread1 = thread {
        lock.withLock { b = 1 }
    }

    thread0.join()
    thread1.join()

    lock.withLock {
        println(a)
        println(b)
    }
}