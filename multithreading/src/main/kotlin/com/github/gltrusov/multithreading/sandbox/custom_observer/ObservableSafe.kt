package com.github.gltrusov.multithreading.sandbox.custom_observer

/**
 * Эта реализацию будет корректно работать в многопоточной среде.
 * Все Observable в Android реализованы схожим образом.
 */
internal class ObservableSafe : Observable {

    private val lock: Any = Any()
    private val observers: MutableSet<Observer> = mutableSetOf()

    override fun registerObserver(observer: Observer) {
        synchronized(lock) {
            observers.add(observer)
        }
    }

    override fun unregisterObserver(observer: Observer) {
        synchronized(lock) {
            observers.remove(observer)
        }
    }

    override fun notifyObservers() {
        //тк в onChanged может быть длительная операция
        //чтобы не блокировать другие потоки в synchronized лучше
        //только получать данные.
        val observersCopy = synchronized(lock) {
            observers.toList()
        }
        observersCopy.forEach { observer -> observer.onChanged() }
    }
}