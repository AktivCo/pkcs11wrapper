plugins {
    java
    application
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
    implementation(fileTree("../pkcs11wrapper/lib.pkcs11wrapper/build/libs/") { include("pkcs11wrapper*.jar") })
    implementation(fileTree("../pkcs11wrapper/lib.pkcs11wrapper-ktx/build/libs/") { include("pkcs11wrapper-ktx*.jar") })

    implementation(Dependencies.pkcs11jna)
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