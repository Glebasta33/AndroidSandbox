package com.github.gltrusov.dagger.di_basics

class Processor {
    override fun toString(): String = "Intel Core i9"
}

class Motherboard {
    override fun toString(): String = "X7 3000"
}

class RAM {
    override fun toString(): String = "16 GB"
}

/**
 * processor, motherboard, ram - это зависимости класса Computer.
 * Класс не должен сам создавать зависимости, они должны поставляться в него извне.
 */
data class Computer(
    val processor: Processor,
    val motherboard: Motherboard,
    val ram: RAM
)