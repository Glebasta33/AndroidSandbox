package com.github.gltrusov.multithreading.sandbox.jmm

import com.github.gltrusov.core.ui.compose.logOnUi
import com.github.gradle_sandbox.Markdown

/**
 * Re-Entrant - Повторно-Входимый - это свойтво synchronized-секций,
 * которое позволяет внутри synchronized-секции повторно входить в
 * другую synchronized-секцию по тому же ключу.
 *
 * Без функциональности Reentrant - был бы dead lock.
 * TODO Сделать пример с dead lock`ом
 * TODO Также есть live lock - изучить отличие от dead lock.
 */
@Markdown("jmm_synchronized_reentrant.md")
fun JmmSynchronizedReentrant() {
    val lock = Any()

    fun check() = synchronized(lock) {
        logOnUi("before sync block")
        synchronized(lock) {
            logOnUi("inside sync block")
        }
    }

    check()
}
fun main() {
    JmmSynchronizedReentrant()
}