buildscript {
    dependencies {
        classpath(Dependencies.androidGradlePlugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

plugins {
    kotlin("android") version Dependencies.kotlin apply false
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

tasks.register("clean") {
    delete(rootProject.buildDir)
}