plugins {
    `java-library`
    id("kotlin")
}

version = AppConfig.wrapperKtxVersionName

tasks.jar {
    archiveBaseName.set("pkcs11wrapper-ktx")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    api(project(":lib.pkcs11wrapper"))
}