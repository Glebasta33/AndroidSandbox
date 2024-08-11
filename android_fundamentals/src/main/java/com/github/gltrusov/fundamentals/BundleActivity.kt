package com.github.gltrusov.fundamentals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.github.gltrusov.fundamentals.databinding.ActivityBundleBinding
import com.github.gradle_sandbox.Markdown
import java.io.Serializable
import kotlin.concurrent.thread

/**
 * Bundle - это обёртка над мапой (ArrayMap - разновидность мапы
 * на Android с более эффективным использованием памяти).
 *
 * В Bundle можно положить примитивы и Parcelable, Serializable (а также их массивы), Binder.
 *
 * Основные сценарии использования:
 * 1. IPC - Bundle используется внутри Intents для передачи данных
 * между компонентами и процессами.
 * 2. State preservation (onSaveInstanceState).
 *
 * Размер Bundle: Google рекомендует не привышать 50kb для saved state.
 */

data class SerializableObject(
    val text: String
) : Serializable

@Markdown("bundle_for_state_preservation.md")
internal class BundleActivity : AppCompatActivity() {

    private var counterInt = MutableLiveData(0)
    private var serializableObject = MutableLiveData(SerializableObject("Counter"))

    private val binding by lazy {
        ActivityBundleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.markdown1.loadMarkdownFile("file:///android_asset/bundle_for_state_preservation.md")

        counterInt.observe(this) {
            binding.textViewCounter.text = "Int: $it"
        }

        serializableObject.observe(this) {
            binding.textViewCounter1.text = it.toString()
        }

        /**
         * Отправка данных в другую активити через Bundle:
         */
        binding.sendDataButton.setOnClickListener {
            val intent = Intent(this, BundleReceiverActivity::class.java)
            val bundle = Bundle()

            bundle.putInt(INT_COUNT_BUNDLE_KEY, counterInt.value ?: 0)
            bundle.putSerializable(SERIALIZABLE_COUNT_BUNDLE_KEY, serializableObject.value)

            intent.putExtras(bundle)

            startActivity(intent)
        }

        runTimer()
    }

    /**
     * Получение данных из Bundle:
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val count = savedInstanceState.getInt(INT_COUNT_BUNDLE_KEY)
        counterInt.value = count

        val serializable = savedInstanceState.getSerializable(SERIALIZABLE_COUNT_BUNDLE_KEY)
        serializableObject.value = serializable as SerializableObject
    }

    /**
     * Сохранение состояния в Bundle:
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(INT_COUNT_BUNDLE_KEY, counterInt.value!!)
        outState.putSerializable(SERIALIZABLE_COUNT_BUNDLE_KEY, serializableObject.value!!)
    }

    private fun runTimer() {
        thread {
            while (true) {
                Thread.sleep(1000)
                val newInt = counterInt.value?.plus(1)
                counterInt.postValue(newInt)

                val newObject = serializableObject.value
                serializableObject.postValue(newObject?.copy(text = "Counter: $newInt"))
            }
        }
    }

    companion object {
        private const val INT_COUNT_BUNDLE_KEY = "count"
        private const val SERIALIZABLE_COUNT_BUNDLE_KEY = "s_count"
        fun start(context: Context): Intent =
            Intent(context, BundleActivity::class.java).apply {
                context.startActivity(this)
            }
    }

}