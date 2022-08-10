plugins {
    `java-library`
}

version = AppConfig.versionName

tasks.jar {
    archiveBaseName.set("pkcs11wrapper")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly(Dependencies.jetbrainsAnnotations)
    api(Dependencies.pkcs11jna)
}
