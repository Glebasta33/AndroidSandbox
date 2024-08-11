package com.github.gltrusov.fundamentals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.fundamentals.databinding.ActivitySerializationBinding
import com.github.gradle_sandbox.Markdown
import java.io.Externalizable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.ObjectOutput
import java.io.ObjectOutputStream
import java.io.Serializable

/**
 * - Сериализация - это процесс перевода
 * структуры данных в последовательность байтов.
 * - Десериализация - создания структуры данных
 * из битовой последовательности.
 *
 * Serializable - пустой маркерный Java-интерфейс.
 *
 * Сериализация под капотом использует Reflection API.
 * Механизм рефлексии позволяет "исследовать" объект и
 * получить информацию о нём.
 * Рефлексия медленная и потребляет много ресурсов.
 * Следовательно это критично для слабого железа Android.
 *
 * Externalizable - расширение Serializable с обязательным
 * переопределением методов чтения и записи (в этом похож на Parcelable) .
 * Externalizable быстрее Serializable в 1,5-2 раза.
 */
internal data class Person(
    private val name: String,
    @Transient private val password: String, //Transient поле игнорируется при сериализации/десериализации
    private val age: Int
) : Serializable

internal data class ExternalizablePerson(
    private var name: String,
    private var age: Int
) : Externalizable {
    constructor() : this("", 0)

    override fun writeExternal(out: ObjectOutput) {
        out.writeObject(name)
        out.writeInt(age)
    }

    //Порядок важен как и Parcelable
    override fun readExternal(`in`: ObjectInput) {
        name = `in`.readObject() as String
        age = `in`.readInt()
    }

}

@Markdown("serialization.md")
internal class SerializationActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySerializationBinding.inflate(layoutInflater)
    }

    private val serializablePerson = Person(
        name = "John",
        password = "password123",
        age = 27
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()

        val filePath = "$filesDir/serialized.bin"
        val file = File(filePath).apply { createNewFile() }

        // Serialization
        // Serializable можно сохранять в постоянную память (файл)
        // в отличие от bytes из Parcelable.
        val fileOutputStream = FileOutputStream(file)
        ObjectOutputStream(fileOutputStream).use {
            it.writeObject(serializablePerson)
            it.flush()
        }


        binding.textView1.text = "Сериализованный объект (содержимое файла): ${file.readText()}"

        // Deserialization
        val fileInputStream = FileInputStream(file)
        ObjectInputStream(fileInputStream).use {
            binding.textView2.text = "Десериализованный объект: ${ it.readObject() as Person }"
        }
    }

    private fun setupUi() {
        setContentView(binding.root)
        binding.markdown1.loadMarkdownFile("file:///android_asset/serialization.md")
    }
}