package com.github.feature_sample.di

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

class FeatureFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(
            this.activity,
            "Feature fragment, activity: ${this.activity?.javaClass?.simpleName}",
            Toast.LENGTH_LONG
        ).show()
    }

}