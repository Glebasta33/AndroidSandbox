package com.github.gradle_sandbox

import com.google.auto.service.AutoService
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.StandardLocation

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@SupportedAnnotationTypes("com.github.gradle_sandbox.Markdown")
class MarkdownAnnotationProcessor : AbstractProcessor() {

    private lateinit var projectPath: Path

    @Suppress("NewApi")
    override fun init(processingEnv: ProcessingEnvironment?) {
        println("MarkdownAnnotationProcessor init")
        super.init(processingEnv)
        val filer = processingEnv?.filer ?: return
        val res = filer.createResource(StandardLocation.CLASS_OUTPUT, "", "tmp", null)
        projectPath = Paths.get(res.toUri()).parent.parent.parent.parent.parent.parent.parent
        res.delete()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Markdown::class.java.name)
    }

    @Suppress("NewApi")
    override fun process(
        set: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment?
    ): Boolean {
        roundEnvironment?.getElementsAnnotatedWith(Markdown::class.java)?.forEach { clazz ->
            val md = clazz.getAnnotation(Markdown::class.java)?.markdownName ?: return false

            val simpleClassName = "${clazz.simpleName}.kt"

            val foundFile = File(projectPath.toUri()).walk().find { it.name == simpleClassName }

            val foundFileSrc = foundFile?.path?.substringBefore("main") ?: return false
            val assetsDir = File(foundFileSrc).walk().find { it.name == "assets" } ?: return false

            val code = StringBuilder()
            foundFile.bufferedReader()
                .readLines()
                .forEach { line ->
                    val isUseful = line.startsWith("import").not()
                            && line.startsWith("package").not()
                            && line.startsWith("@Markdown").not()

                    if (isUseful) {
                        code.append(line).append("\n")
                    }
                }
            val mdText = "### ${clazz.simpleName} \n" +
                    "``` kotlin \n" +
                    code + "\n" +
                    "```"

            println("MarkdownAnnotationProcessor: \nfoundFile: $foundFile\nassetsDir: $assetsDir\nfoundFileSrc: $foundFileSrc \n\n")

            val mdFile = File("$assetsDir\\$md")
            mdFile.createNewFile()
            mdFile.writeText(mdText)
        }
        return true
    }
}

