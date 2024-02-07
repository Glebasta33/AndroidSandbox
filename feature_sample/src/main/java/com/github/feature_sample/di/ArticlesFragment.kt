package com.github.feature_sample.di


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.github.feature_sample.R
import com.github.feature_sample.databinding.FragmentArticlesBinding
import dagger.Lazy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    @Inject
    internal lateinit var articleViewModelFactory: Lazy<ArticlesViewModel.Factory>

    private val articlesViewModel: ArticlesViewModel by viewModels {
        articleViewModelFactory.get()
    }

    private var adapter: ArticleAdapter? = null

    override fun onAttach(context: Context) {
        /**
         * Чтобы работать с компонентов фичи, получаем её из ViewModel, к которому компонент привязан.
         */
        ViewModelProvider(this).get<ArticlesComponentViewModel>()
            .newDetailsComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleAdapter = ArticleAdapter()
        this.adapter = articleAdapter

        val binding = FragmentArticlesBinding.bind(view)
        binding.list.adapter = articleAdapter

        articlesViewModel.articles.onEach { articles ->
            adapter?.submitList(articles)
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    companion object {
        fun newInstance() = ArticlesFragment()
    }
}