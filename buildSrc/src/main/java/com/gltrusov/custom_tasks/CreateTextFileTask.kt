package com.gltrusov.custom_tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

open class CreateTextFileTask : DefaultTask() {

    /**
     * Аннотация регистрирует поле в TaskOutputs.
     * RegularFileProperty - ленивая обёртка над File.
     * project.objects: ObjectFactory - фабрика для создания обёрток.
     * convention - устанавливает значение по умолчанию.
     */
    @OutputFile
    val outputFile: RegularFileProperty = project.objects.fileProperty()
        .convention { project.file("files/created_by_custom_task.txt") }

    @TaskAction
    fun execute() {
        val outputText = "Hello, Gradle, from custom task!!!"
        outputFile.get().asFile.writeText(outputText)
    }

}