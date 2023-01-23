import org.jetbrains.gradle.ext.copyright
import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers

setBuildDir("gradleBuild")

plugins {
    kotlin("jvm") version AppConfig.kotlinVersion apply false
    idea_ext
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

    copyright {
        useDefault = "bsd-copyright"
        profiles {
            create("bsd-copyright") {
                notice = file("../copyright-template").readText(Charsets.UTF_8).trimIndent()
                keyword = "Copyright"
            }
        }
    }
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}