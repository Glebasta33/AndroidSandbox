package com.github.gltrusov.dagger.androidbroadcast.c5_multimodularity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.feature_sample.di.ArticlesFragment
import com.github.gltrusov.R

class BaseMainActivity : AppCompatActivity(R.layout.activity_main_legacy) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentManager = supportFragmentManager
        /**
         * Фрагмент из фича-модуля создаётся в app-модуле:
         */
        if (fragmentManager.findFragmentById(R.id.nav_host_fragment) == null) {
            fragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment, ArticlesFragment.newInstance())
                .commit()
        }
    }

    private companion object {

        private const val FRAGMENT_ARTICLES = "articles"
    }
}