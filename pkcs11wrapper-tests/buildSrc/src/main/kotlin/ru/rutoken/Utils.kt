package ru.rutoken

import org.gradle.api.Project
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

fun requireFileInDirectory(directory: String, file: String) =
    check(File(directory, file).exists()) { "Not found $file in directory $directory" }

fun copyFile(file: String, sourcePath: String, destinationPath: String) {
    requireFileInDirectory(sourcePath, file)
    Files.createDirectories(Path.of(destinationPath))
    Files.copy(Path.of(sourcePath, file), Path.of(destinationPath, file), StandardCopyOption.REPLACE_EXISTING)
}

fun copyFromBinaryDeps(
    rootDir: String,
    projectName: String,
    architecture: String,
    file: String,
    destinationPath: String
) {
    val sourcePath = "$rootDir/external/$projectName/$architecture/lib"
    copyFile(file, sourcePath, destinationPath)
}

val Project.isArchFlavorsEnabled: Boolean
    get() = !hasProperty("disableArchFlavors")
            || (hasProperty("disableArchFlavors") && property("disableArchFlavors") == "false")
