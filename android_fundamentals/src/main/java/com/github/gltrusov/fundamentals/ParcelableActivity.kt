package com.github.gltrusov.fundamentals

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.fundamentals.databinding.ActivityParcelableBinding
import com.github.gradle_sandbox.Markdown
import java.nio.charset.StandardCharsets


/**
 * Особенности сериализации/десериализации через Parcelable:
 * - Значения полей задаётся на прямую (по порядку) - не нужно хранить названия полей.
 * - Сами создаём объект, без рефлексии.
 *
 * Данные вставляются в Parcel. Parcel - это контейнер для данных,
 * который может отправлен через IBinder.
 *
 * Parcelable быстрее Serializable в 6-7 раз,
 * и по использыванию памяти на лучше на 30%.
 */
internal data class ParcelablePerson(
    private val name: String,
    private val age: Int
) : Parcelable {
    // Вторичный конструктор, достающий поля из Parcel
    constructor(parcel: Parcel) : this( //десериализация
        parcel.readString().toString(), //1
        parcel.readInt() //2
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) { //сериализация.
        //Порядок важен:
        parcel.writeString(name) //1
        parcel.writeInt(age) //2
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelablePerson> {
        override fun createFromParcel(parcel: Parcel): ParcelablePerson {
            return ParcelablePerson(parcel)
        }

        override fun newArray(size: Int): Array<ParcelablePerson?> {
            return arrayOfNulls(size)
        }
    }

}

@Markdown("parcelable.md")
class ParcelableActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityParcelableBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()

        val parcelablePerson = ParcelablePerson(
            name = "John",
            age = 33
        )

        val sourceParcel: Parcel = Parcel.obtain()
        sourceParcel.writeParcelable(parcelablePerson, 0)
        val bytes = sourceParcel.marshall() //эти байты нельзя хранить в постоянной памяти.
        sourceParcel.recycle()

        val destinationParcel = Parcel.obtain()
        destinationParcel.unmarshall(bytes, 0, bytes.size)
        destinationParcel.setDataPosition(0)

        val classLoader = ParcelablePerson::class.java.classLoader
        val result = destinationParcel.readParcelable<ParcelablePerson>(classLoader)
        destinationParcel.recycle()

        binding.textView1.text = "Байты: ${String(bytes, StandardCharsets.UTF_8)}"
        binding.textView2.text = "Объект: $result"
    }

    private fun setupUi() {
        setContentView(binding.root)
        binding.markdown1.loadMarkdownFile("file:///android_asset/parcelable.md")
    }
}