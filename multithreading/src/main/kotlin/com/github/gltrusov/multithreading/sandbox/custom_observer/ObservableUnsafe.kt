package com.github.gltrusov.multithreading.sandbox.custom_observer

/**
 * Эта реализацию будет корректно работать только в 1 потоке.
 * В многопоточной среде будут ошибки и race condition.
 */
internal class ObservableUnsafe : Observable {

    private val observers: MutableSet<Observer> = mutableSetOf()

    override fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun unregisterObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers() {
        observers.forEach { observer -> observer.onChanged() }
    }
}