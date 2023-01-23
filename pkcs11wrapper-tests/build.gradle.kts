import org.jetbrains.gradle.ext.copyright
import org.jetbrains.gradle.ext.settings

buildscript {
    dependencies {
        classpath(Dependencies.androidGradlePlugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

plugins {
    kotlin("android") version Dependencies.kotlin apply false
    idea_ext
}

setBuildDir("gradleBuild")

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
    tasks.withType<Test> {
        testLogging {
            setExceptionFormat("full")
            events("started", "skipped", "passed", "failed")
            showStandardStreams = true
        }
    }
    // Uncomment to build fat tests, like on CI
//    ext.set("disableArchFlavors", "true")
}

idea.project?.settings {
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