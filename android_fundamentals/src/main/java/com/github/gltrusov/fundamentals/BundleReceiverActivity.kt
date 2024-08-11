package com.github.gltrusov.fundamentals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.fundamentals.databinding.ActivityBundleReceiverBinding
import com.github.gradle_sandbox.Markdown


@Markdown("bundle_receiver_activity.md")
internal class BundleReceiverActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBundleReceiverBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val launchingIntent = intent

        val bundle = launchingIntent.extras

        binding.textViewCounter.text = bundle?.getInt(INT_COUNT_BUNDLE_KEY).toString()
        binding.textViewCounter1.text = bundle?.getSerializable(SERIALIZABLE_COUNT_BUNDLE_KEY).toString()

        binding.markdown1.loadMarkdownFile("file:///android_asset/bundle_receiver_activity.md")
    }

    companion object {
        private const val INT_COUNT_BUNDLE_KEY = "count"
        private const val SERIALIZABLE_COUNT_BUNDLE_KEY = "s_count"
    }
}