package com.github.gltrusov.interview_fuckups.assets

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.github.gltrusov.R
import com.github.gltrusov.ui.theme.AndroidSandboxTheme
import com.google.accompanist.drawablepainter.rememberDrawablePainter

/**
 * Помимо res в Android есть ещё один каталог, в котором можно хранить ресурсы - assets.
 * По умолчанию проект в студии не содержит данную папку. Чтобы её создать, нужно выбрать: File | New | Folder | Assets Folder.
 *
 * Assets позволяет добавить в проект файлы произвольного формата (как в Raw).
 *
 * Для файлов в assets не генерятся id ресурсов, а указывается путь к файлу.
 * assets в отличие от res позволяет создавать внутри любые пакеты с любой глубиной вложенности.
 */
class AssetsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val text = readTextFromAsset()
        val image = loadImageFromAsset()
        val font = getTypeface()

        setContent {
            AndroidSandboxTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column {
                        Text(
                            text = "Text: $text"
                        )
                        Image(painter = rememberDrawablePainter(drawable = image), null)
                    }
                }
            }
        }


    }

    /**
     * Через контекст приложения можно обратиться к assets: AssetManager,
     * который позволяет получить доступ к файлу в каталоге assets.
     * Далее вызывается метод AssetManager.open(fileName: String): InputStream, который принимает имя файла и возвращает InputStream.
     */
    private fun readTextFromAsset(): String {
        val text: String = applicationContext.assets
            .open("text/text_example.txt")
            .bufferedReader().use {
                it.readText()
            }.also { textFromAssets ->
                Log.d("MyTest", textFromAssets) // Hello from text example!
            }
        return text
    }

    /**
     * Также в assets можно хранить кастомный шрифты!
     */
    private fun getTypeface(): Typeface? {
        return try {
            Typeface.createFromAsset(assets, "fonts/autumnal.otf")
        } catch (e: Exception) {
            Log.d("MyException", e.message.toString()) // Font asset not found fonts/autumnal.otf ???
            null
        }
    }

    /**
     * Из assets можно читать любые файлы, в том числе изображения.
     * Метод open возвращает InputStream, который нужно преобразовать в объект соответсвующего типа.
     */
    private fun loadImageFromAsset(): Drawable? {
        val image: Drawable? = applicationContext.assets
            .open("images/cat_image.jpg")
            .use { inputStream ->
                Drawable.createFromStream(inputStream, null)
            }
        return image
    }


    /**
     * Также можно хранить файлы в res-дериктории - raw.
     * Доступ к файлу осуществляется по id, что более безопасно, но менее гибко, чем в assets.
     */
    private fun loadImageFromRaw(): Drawable? {
        val image: Drawable? = resources.openRawResource(R.raw.cat_image)
            .use { inputStream ->
                Drawable.createFromStream(inputStream, null)
            }
        return image
    }

}