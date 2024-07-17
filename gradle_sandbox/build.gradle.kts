import com.gltrusov.custom_tasks.CreateTextFileTask
import com.gltrusov.custom_tasks.PrintFileContentTask
import com.gltrusov.custom_tasks.PrintKotlinFileContentTask


// ./gradlew printHelloGradle
// TaskProvider - для ускорения выполнения таски
val printHelloGradle: TaskProvider<Task> by tasks.registering {
    doFirst {
        println("Hello, Gradle 1")
    }
    doLast {
        println("Hello, Gradle 2")
    }
}

// создание файла и вставка текста в него
val createTextFile by tasks.registering {
    outputs.file("files/created_by_gradle.txt")

    doFirst {
        val outputFile = outputs.files.singleFile
        outputFile.writeText("Hello, Gradle from file")
    }
}

// получение outputs из createTextFile и вставка их в inputs printFileContent c последующей печатью
val printFileContent by tasks.registering {
    inputs.file(createTextFile.map { it.outputs.files.singleFile })
    doFirst {
        val inputFile = inputs.files.singleFile
        println(inputFile.readText())
    }
}

//расшерение таски createTextFile
tasks.named("createTextFile").configure {
    doLast {
        val outputFile = outputs.files.singleFile
        outputFile.appendText("\nAdditional text")
    }
}

val createTextFileCustom by tasks.registering(CreateTextFileTask::class) {
    outputFile.set(project.layout.projectDirectory.file("files/created_by_custom_task.txt"))
}

val printFileCustom by tasks.registering(PrintFileContentTask::class) {
    inputFile.set(createTextFileCustom.flatMap { it.outputFile })
}

val printKotlinFile by tasks.registering(PrintKotlinFileContentTask::class)