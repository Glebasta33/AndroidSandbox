package com.github.gltrusov.graphics.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.graphics.R

class TestFeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_test)
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, TestFeatureActivity::class.java)
    }

}