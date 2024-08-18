package com.github.gltrusov.multithreading.sandbox.jmm

import kotlin.concurrent.thread

/**
 * Volatile - даёт гарантии видимости (__Visibility__) изменений данных.
 * Любой поток, использующий данные, помеченные @Volatile, будет иметь гарантии,
 * что он видит актуальные значения.
 * НО Volatile НЕ даёт гарантии атомартности операций по изменению данных!
 * (Volatile решает только проблему видимости).
 */
@Volatile
private var flag: Boolean = true

fun main() {
    val thread0 = thread {
        Thread.sleep(3)
        flag = false
    }
    val thread1 = thread {
        while(flag) {
            Thread.sleep(1)
            println("${Thread.currentThread().name} is working")
        }
    }

    thread0.join()
    thread1.join()
}