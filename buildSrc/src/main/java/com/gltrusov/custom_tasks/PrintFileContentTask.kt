package com.gltrusov.custom_tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

open class PrintFileContentTask : DefaultTask() {

    @InputFile
    val inputFile: RegularFileProperty = project.objects.fileProperty()

    @TaskAction
    fun execute() {
        println(inputFile.get().asFile.readText())
    }
}