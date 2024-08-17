package com.github.gltrusov.multithreading.sandbox.fundamentals

/**
 * Re-Entrant - Повторно-Входимый - это свойтво synchronized-секций,
 * которое позволяет внутри synchronized-секции повторно входить в
 * другую synchronized-секцию по тому же ключу.
 *
 * Без функциональности Reentrant - был бы dead lock.
 * TODO Сделать пример с dead lock`ом
 * TODO Также есть live lock - изучить отличие от dead lock.
 */
fun main() {
    val lock = Any()

    fun check() = synchronized(lock) {
        println("before sync block")
        synchronized(lock) {
            println("inside sync block")
        }
    }

    check()
}