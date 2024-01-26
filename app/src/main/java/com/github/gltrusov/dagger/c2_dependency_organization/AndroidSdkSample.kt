package com.github.gltrusov.dagger.c2_dependency_organization

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.gltrusov.ui.theme.AndroidSandboxTheme
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DaggerExampleActivity : ComponentActivity() {

    /**
     * ## Lazy и Provider
     * При инджекте Dagger по умолчанию полностью создаёт весь граф объектов,
     * даже если объект не нужен в данный момент. Это может значительно замедлять запуск приложения и экранов.
     *
     * Обёртка Lazy позволяет отложить инициализацию объекта до момента, когда он явно не понадобится в коде (вызов метода .get()).
     * Provider позволяет при каждом обращении к нему, получать зависимость прямо из графа (а не из поля).
     * Это может быть полезно, когда нужно каждый раз получать новый экземпляр.
     *
     * P.S. Lazy и Provider не работают с зависимостями, которые используют Assisted Inject
     */
    @Inject
    lateinit var vmAssistedFactory: NewsDetailsViewModelFactory.Factory
    private val viewModel: NewsDetailsViewModel by viewModels {
        vmAssistedFactory
            .create("news_id_123") // Создание фабрики VM, поставляемой через @AssistedInject.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.toString()
        setContent {
            val state = viewModel.news.collectAsState()
            AndroidSandboxTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Hello ${state.value.title}")
                    }
                }
            }
        }
    }

    /**
     * ## Inject в метод
     * Помимо иньекции в поля и конструктор класса, можно инджектить зависимости в метод.
     * (Например, это нужно, когда зависимость нужно только для одноразового использования, и нет смысла
     * хранить на неё ссылку внутри класса).
     *
     * При @Inject в метод поставляются зависимости, указанные в параметрах.
     * При этом метод вызывается сам, в момент иньекции зависимостей в класс (при инициализации).
     * Данный метод не нужно дополнительно где-то вызывать.
     */
    @Inject
    fun trackOnStart(analytics: Analytics) {
        analytics.trackScreenShow()
    }
}

class NewsDetailsViewModel(
    private val newsId: String,
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val newsMock = News("id", "title", "body")

    private val _news = MutableStateFlow(newsMock)
    val news = _news.asStateFlow()

    init {
        Log.d("MyTest", "NewsDetailsViewModel init")
        viewModelScope.launch {
            _news.update {
                // newsRepository.getNews(newsId)
                newsMock
            }
        }
    }
}

/**
 * Иногда не все зависимости, необходимые для создания объекта, есть в графе.
 * В таких ситуациях можно использовать @AssistedInject.
 * В данном случае, не все параметры конструктора имеются в графе, и нужно "помочь" Dagger`у создать этот объект.
 *
 * Те параметры, типы которых не имеются в графе, нужно пометить аннотацией Assisted (параметр с именем нужен, чтобы отличить параметры одного типа).
 *
 */
class NewsDetailsViewModelFactory @AssistedInject constructor(
    @Assisted("newsId") private val newsId: String,
    private val newsRepository: NewsRepository,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        require(modelClass == NewsDetailsViewModel::class)

        Log.d("MyTest", "NewsDetailsViewModelFactory create")
        return NewsDetailsViewModel(newsId, newsRepository) as T
    }

    /**
     * Данная фабрика - это фабрика по созданию объекта с @AssistedInject (в данном случае NewsDetailsViewModelFactory).
     * В методе create параметр, который пробрасывется для создания объекта, нужно пометить так же, как в конструкторе.
     */
    @AssistedFactory
    interface Factory {
        fun create(@Assisted("newsId") newsId: String): NewsDetailsViewModelFactory
    }
}