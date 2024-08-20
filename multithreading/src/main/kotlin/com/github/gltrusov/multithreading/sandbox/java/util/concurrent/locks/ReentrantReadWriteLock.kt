package com.github.gltrusov.multithreading.sandbox.java.util.concurrent.locks

import com.github.gltrusov.multithreading.sandbox.android.custom_observer.Observable
import com.github.gltrusov.multithreading.sandbox.android.custom_observer.Observer
import com.github.gradle_sandbox.Markdown
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * ReentrantReadWriteLock - расширенный вариант ReentrantLock, который внутри себя имеет 2 отдельных
 * лока: 1 - на чтение, 2 - на запись.
 */
@Markdown("reentrant_read_write_lock.md")
internal class ReentrantReadWriteLock : Observable {

    private val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()
    private val observers: MutableSet<Observer> = mutableSetOf()

    override fun registerObserver(observer: Observer) {
        lock.write { observers.add(observer) }
    }

    override fun unregisterObserver(observer: Observer) {
        lock.write { observers.remove(observer) }
    }

    override fun notifyObservers() {
        val observersCopy = lock.read { observers.toList() }
        observersCopy.forEach { observer -> observer.onChanged() }
    }

}