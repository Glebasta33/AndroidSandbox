package com.github.gltrusov.dagger.c6_hilt

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
import androidx.lifecycle.viewModelScope
import com.github.gltrusov.ui.theme.AndroidSandboxTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Активити помечаем аннотацией @AndroidEntryPoint (этой же аннотацией нужно помечать фрагмент)
 */
@AndroidEntryPoint
class HiltExampleActivity : ComponentActivity() {

    /**
     * Создание hiltViewModel через делигат (без фабрики).
     */
    private val newsViewModel: NewsDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * Не нужно явно самому инджектить в активити:
         */
//        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        //Сеттим поле ViewModel
        newsViewModel.newsId = "id"
    }

    override fun onResume() {
        super.onResume()
        setContent {
            AndroidSandboxTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val state = newsViewModel.news.collectAsState()
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Hello ${state.value.title}")
                    }
                }
            }
        }
    }

    @Inject
    fun trackOnStart(analytics: Analytics) {
        analytics.trackScreenShow()
    }
}

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    /**
     * В Hilt нет AssistedInject, но можно установить свойтсва извне:
     */
    lateinit var newsId: String

    private val newsMock = News("id", "Hilt", "body")

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
 * Фабрика не нужна в Hilt:
 */
//class NewsDetailsViewModelFactory @AssistedInject constructor(
//    @Assisted("newsId") private val newsId: String,
//    private val newsRepository: NewsRepository,
//) : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
////        require(modelClass == NewsDetailsViewModel::class)
//
//        Log.d("MyTest", "NewsDetailsViewModelFactory create")
//        return NewsDetailsViewModel(newsId, newsRepository) as T
//    }
//
//    @AssistedFactory
//    interface Factory {
//        fun create(@Assisted("newsId") newsId: String): NewsDetailsViewModelFactory
//    }
//}