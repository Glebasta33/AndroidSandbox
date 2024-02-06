package com.github.gltrusov.dagger.c4_multibinding.set

import com.github.gltrusov.dagger.c3_subcomponents.component.AppScope
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Inject

/**
 * ## Mutibinding - фича Dagger, которая позволяет группировать зависимости в Set или Map, в которые
 * можно эффективно добавлять новые элементы и распрастронять во все места, где необходим сборник зависимостей определённого типа.
 *
 * Задача: допустим есть 3 трекера аналитики, которые реализуют один интерфейс AnalyticsTracker.
 */
interface AnalyticsTracker {

    fun trackEvent(event: Event) {
        println("${this}: $event")
    }

    data class Event(val name: String, val value: String)
}

class FirebaseAnalyticsTracker @Inject constructor() : AnalyticsTracker {

    override fun toString() = "FirebaseAnalytics"
}

class FacebookAnalyticsTracker @Inject constructor() : AnalyticsTracker {

    override fun toString() = "FacebookAnalytics"
}

class AppMetricaTracker @Inject constructor() : AnalyticsTracker {

    override fun toString() = "AppMetrica"
}

/**
 * Также есть класс Analytics, который собирает все трекеры аналитики и проходится по ним,
 * вызывая метод trackEvent.
 * При multibinding можно навесить @Inject на конструктор и передавать в него Set с зависимостями.
 *
 * Dagger - это Java-фраймворк и он работает с Java-типами, но Kotlin имеет свои особенности работы с дженериками,
 * и чтобы не получить проблем, нужно пометить Set аннотацией @JvmSuppressWildcards. Объявление Set в Kotlin
 * выглядит как Set<out T>, аннотация @JvmSuppressWildcards заглушает out, чтобы это не влияло на Java.
 */
class Analytics @Inject constructor(
//    private val trackers: List<AnalyticsTracker>
    private val trackers: Set<@JvmSuppressWildcards AnalyticsTracker>
) {
    fun trackLogEvent(event: AnalyticsTracker.Event) {
        trackers.forEach { tracker ->
            tracker.trackEvent(event)
        }
    }
}

@Component(modules = [AppModule::class])
@AppScope
interface AppComponent {

    val analytics: Analytics //<- объявление зависимости для теста

}

@Module
interface AppModule {

    /**
     * Данное решение не гибкое, тк при добавлении нового трекера, нужно будет модифицировать
     * данный Provides-метод.
     */
//    @Provides
//    fun provideAnalytics(
//        firebaseAnalyticsTracker: FirebaseAnalyticsTracker,
//        facebookAnalyticsTracker: FacebookAnalyticsTracker,
//        appMetricaTracker: AppMetricaTracker
//    ): Analytics = Analytics(
//        listOf(firebaseAnalyticsTracker, facebookAnalyticsTracker, appMetricaTracker)
//    )

    /**
     * Multibinding позволяет реализовать более гибкое решение, чтобы не модифицировать в ручную Provide-метод.
     * Чтобы сбайндить зависимости в Set, все элементы должны быть одного типа.
     */
    @Binds
    @IntoSet
    fun bindFbAnalyticsTracker(facebookAnalyticsTracker: FacebookAnalyticsTracker): AnalyticsTracker

    @Binds
    @IntoSet
    fun bindFirebaseAnalyticsTracker(firebaseAnalyticsTracker: FirebaseAnalyticsTracker): AnalyticsTracker

    @Binds
    @IntoSet
    fun bindAppMetricaTracker(appMetricaTracker: AppMetricaTracker): AnalyticsTracker

    /**
     * Помимо того, что можно добавлять под одной зависимости в Set, можно добавлять и сразу несколько с помощью аннотации @ElementsIntoSet
     *
     *     @Provides
     *     @ElementsIntoSet
     *     fun provideMultipleTrackers(
     *         firebaseAnalyticsTracker: FirebaseAnalyticsTracker,
     *         appMetricaTracker: AppMetricaTracker
     *     ): Set<AnalyticsTracker> = setOf(firebaseAnalyticsTracker, appMetricaTracker)
     */
    fun foo()
}

fun main() {
    val appComponent: AppComponent = DaggerAppComponent.create()
    val event: AnalyticsTracker.Event = newLogEvent()
    appComponent.analytics.trackLogEvent(event)
//    AppMetrica: Event(name=log, value=none)
//    FirebaseAnalytics: Event(name=log, value=none)
//    FacebookAnalytics: Event(name=log, value=none)
}

fun newLogEvent(): AnalyticsTracker.Event {
    return AnalyticsTracker.Event("log", "none")
}