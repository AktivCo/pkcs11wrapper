setBuildDir("gradleBuild")

plugins {
    kotlin("jvm") version AppConfig.kotlinVersion apply false
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

tasks.register("clean") {
    delete(rootProject.buildDir)
}