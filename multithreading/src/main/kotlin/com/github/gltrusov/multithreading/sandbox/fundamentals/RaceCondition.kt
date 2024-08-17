package com.github.gltrusov.multithreading.sandbox.fundamentals

import kotlin.concurrent.thread

/**
 * RACE CONDITION.
 *
 * Изменения стейта будет непредсказуемым:
 *
 * Thread-0 b == 0  -  если отработают последовательно 0, 1
 * Thread-1 a == 1
 *
 * Thread-1 a == 1  - (!) сложный кейс, когда потоки выполняютя параллельно, но внутренний операции "перемешались".
 * Thread-0 b == 0
 *
 * Thread-0 b == 1  -  если потоки выполнятся параллельно
 * Thread-1 a == 1
 *
 * Thread-0 a == 0  -  если отработают последовательно 0, 1
 * Thread-1 b == 1
 *
 * Особенности, которые приводят к race condition:
 * - На уровне машинных команд процессора тут происхдит множество комманд типа: READ, MOVE, WRITE...
 * - При этом у потоков имеется кэш, в который они копируют данные (__Visibility__).
 * (то есть другие потоки НЕ ВИДЯТ, что происходит с данными, которые поток прихранил в своём кеше).
 * - Компилятор может поменять инструкции местами в байт-коде (__Reordering__):
 * ```
 *         a = 1                println(b)
 *         println(b)   --->    a = 1
 * ```
*/
fun main() {
    var a = 0
    var b = 0

    val thread0 = thread {
        a = 1
        println("${Thread.currentThread().name} b == $b")
    }

    val thread1 = thread {
        b = 1
        println("${Thread.currentThread().name} a == $a")
    }

    thread0.join()
    thread1.join()
}


private object C