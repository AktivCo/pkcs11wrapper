package ru.rutoken

import org.gradle.api.Project
import java.io.ByteArrayOutputStream
import java.io.File

fun Project.runCommand(command: String, currentWorkingDir: File = file("./")): String {
    val byteOut = ByteArrayOutputStream()
    project.exec {
        workingDir = currentWorkingDir
        commandLine = command.split("\\s".toRegex())
        standardOutput = byteOut
    }
    return String(byteOut.toByteArray()).trim()
}