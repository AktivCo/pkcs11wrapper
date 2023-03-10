import org.jetbrains.gradle.ext.copyright
import org.jetbrains.gradle.ext.settings

plugins {
    java
    application
    idea_ext
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    google()
    mavenCentral()
    maven {
        url = uri("https://nexus.aktivco.ru/repository/maven-public")
    }
}

dependencies {
    compileOnly(Dependencies.jetbrainsAnnotations)

    implementation(fileTree("../pkcs11wrapper/lib.pkcs11wrapper/build/libs/") { include("pkcs11wrapper*.jar") })

    implementation(Dependencies.pkcs11jna)
    implementation(Dependencies.bouncyCastleBcpkixJdk15on)
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

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.rutoken.samples.GlobalRun"
    }
    // We make a "fat" jar to store module dependencies and classes in one archive
    from(configurations.compileClasspath.get().filter { it.exists() }.map { if (it.isDirectory) it else zipTree(it) })
    // We do not need any signing-related files taken from dependencies
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}