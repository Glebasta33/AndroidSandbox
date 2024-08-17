package com.github.gltrusov.multithreading.sandbox.fundamentals


/**
 * ЖЦ потока:
 * - New - создание инстанса без запуска
 * - Runnable - .start()
 *      - Waiting - .join()
 *      - Blocked - .lock()
 *      - Timed waiting - .wait(timeout)
 * - Terminated
 */
private class MyTask : Runnable {
    override fun run() {
        val i: Int = 0 // сохраняется в локальном стеке потока
        Thread.sleep(1000)
        println("MyTask. Thread name: ${Thread.currentThread().name}, i = $i")
    }
}

private class MyThread : Thread() {
    override fun run() {
        super.run()
        val i: Int = 1 // сохраняется в локальном стеке потока
        sleep(2000)
        println("MyThread. Thread name: ${currentThread().name}, i = $i")
    }
}

fun main() {
    val myTask = MyTask()
    val thread0 = Thread(myTask)

    val thread1 = MyThread()

    thread0.run() // просто вызов кода в run на Main-потоке
    thread0.start() // запуск потока

    thread1.start()

    // СИНХРОНИЗАЦИЯ:
    thread0.join() // main дожидается
    thread1.join() // завершения фоновых потоков.

    // и продолжает выполнение...
    println("Main. Thread name: ${Thread.currentThread().name}")
}