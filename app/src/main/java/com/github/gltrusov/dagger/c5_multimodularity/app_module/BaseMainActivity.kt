package com.github.gltrusov.dagger.c5_multimodularity.app_module

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.feature_sample.di.ArticlesFragment
import com.github.gltrusov.R

class BaseMainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentManager = supportFragmentManager
        if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
            fragmentManager.beginTransaction()
                .add(R.id.fragment_container, ArticlesFragment.newInstance())
                .commit()
        }
    }

    private companion object {

        private const val FRAGMENT_ARTICLES = "articles"
    }
}