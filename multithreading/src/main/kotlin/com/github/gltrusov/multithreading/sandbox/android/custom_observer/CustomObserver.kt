package com.github.gltrusov.multithreading.sandbox.android.custom_observer

internal interface Observer {
    fun onChanged()
}

internal interface Observable {
    fun registerObserver(observer: Observer)

    fun unregisterObserver(observer: Observer)

    fun notifyObservers()
}