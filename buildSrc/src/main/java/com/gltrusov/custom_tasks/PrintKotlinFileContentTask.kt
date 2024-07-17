package com.gltrusov.custom_tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

// поиск аннотированных классов в проекте https://stackoverflow.com/questions/50955626/get-all-classes-with-an-annotation-and-add-them-to-a-hashmap-in-android
// можно сделать аннотацию, которая будет принимать String параметр с путём и названием md-файла
// этой аннотацией можно помечать классы, для которых нужно сгенерить md-файл!
// потом этот же путь и название md-файла будет удобно переиспользовать при вставке в MarkdownView

open class PrintKotlinFileContentTask : DefaultTask() {

    @InputFile
    val inputFile: RegularFileProperty = project.objects.fileProperty()
        .convention {
            val path = "${project.rootDir.path.replace("\", ", "/")}/" +
                    "background/src/main/java/com/github/gltrusov/background/services/job_intent_service/MyJobIntentService.kt"
            project.file(path)
        }

    @OutputFile
    val outputFile: RegularFileProperty = project.objects.fileProperty()
        .convention { project.file("files/markdown.md") }

    @TaskAction
    fun execute() {

        val file = inputFile.get().asFile
        val code = StringBuilder()
        file.bufferedReader().readLines().forEach { line ->
            println(line)
            if (line.startsWith("import").not() && line.startsWith("package").not()) {
                code.append(line).append("\n")
            }
        }
        val mdText = "### Source code \n\n" +
                "``` kotlin \n" +
                code.toString() + "\n" +
                "```"
        outputFile.get().asFile.writeText(mdText)
    }
}