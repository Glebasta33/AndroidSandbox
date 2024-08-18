package com.github.gltrusov.multithreading.sandbox.java.util.concurrent.sync_collections

import com.github.gltrusov.multithreading.sandbox.android.custom_observer.Observable
import com.github.gltrusov.multithreading.sandbox.android.custom_observer.Observer
import java.util.concurrent.CopyOnWriteArrayList

/**
 * CopyOnWriteArrayList похож на atomic (все операции атомарны), это потоко-безопасная обёртка над ArrayList.
 * Под капотом тоже использует volatile для value.
 * При любом изменении списка, список копируется в синхронной секции.
 * TODO: Реализовать кейс с использованием ConurrentHashMap.
 */
internal class ObservableCopyOnWriteArrayList : Observable {

    private val observers: CopyOnWriteArrayList<Observer> = CopyOnWriteArrayList()

    override fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun unregisterObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers() {
        observers.forEach { observer -> observer.onChanged() } // CopyOnWriteArrayList возращает копию списка
    }

}