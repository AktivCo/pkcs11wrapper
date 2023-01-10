import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers

setBuildDir("gradleBuild")

plugins {
    kotlin("jvm") version AppConfig.kotlinVersion apply false
    id("org.jetbrains.gradle.plugin.idea-ext") version AppConfig.ideaExtPluginVersion
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://nexus.aktivco.ru/repository/maven-public")
        }
    }
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Xlint:deprecation")
    }
}

idea.project?.settings {
    rootProject.getTasksByName("assemble", true).forEach { assembleTask ->
        taskTriggers {
            afterBuild(assembleTask)
            afterRebuild(assembleTask)
        }
    }
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}