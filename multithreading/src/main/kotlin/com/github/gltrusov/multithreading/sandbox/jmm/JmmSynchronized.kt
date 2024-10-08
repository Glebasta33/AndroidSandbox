package com.github.gltrusov.multithreading.sandbox.jmm

import com.github.gltrusov.core.ui.compose.logOnUi
import com.github.gradle_sandbox.Markdown
import kotlin.concurrent.thread

/**
 * Java Memory Model (JMM) - это:
 * - Часть спецификации языка (набор правил).
 * - __Описывает отношение между кодом и памятью__
 * - Закрепляет гарантии видимости изменений (Visibility данных)
 * - Определяет атомарность определённфх операций.
 *
 * Атомартность (НЕДЕЛИМОСТЬ) означает, что один поток может видеть данные
 * либо до начала работы над ними другого потока,
 * либо после окончания работы над ними другого потока,
 * НО не сможет вклиниться по середине работы другого потока.
 *
 * ## Synchronized
 * synchronized - одна из ключевых конструкций, поддерживающих JMM.
 * Блок-synchronized создаёт участок кода, который защищён от таких проблем как Visibility, Reordering, Non-Atomic.
 * Код внутри synchronized от начала и до конца исполняется только одним потоком,
 * а другой потом может получить к нему доступ только после полного завершения работы над этим кодом предыдущего потока.
 *
 * Тут возможно только
 * Thread-0 b == 0
 * Thread-1 a == 1
 * либо
 * Thread-0 a == 0
 * Thread-1 b == 1
 * но всегда будет 0 - 1, потому что всегда сначала отработает один поток, потом - другой.
 */
@Markdown("jmm_synchronized.md")
fun JmmSynchronized() {
    // Монитор, объект синхронизации (также называют Mutex (mutual exclusion), Lock)
    // Монитор несёт информацию потокам "занято" (аналогия: комната, которая может быть занята только одним потоком).
    // Монитором может быть любой объект.
    val lock = Any()

    var a = 0
    var b = 0

    val thread0 = thread {
        synchronized(lock) {
            a = 1
            logOnUi("${Thread.currentThread().name} b == $b")
        }

    }

    val thread1 = thread {
        synchronized(lock) {
            b = 1
            logOnUi("${Thread.currentThread().name} a == $a")
        }
    }

    thread0.join()
    thread1.join()
}
fun main() {
    JmmSynchronized()
}