import com.android.build.gradle.internal.tasks.factory.dependsOn
import java.io.File
import ru.rutoken.copyFromBinaryDeps
import ru.rutoken.isArchFlavorsEnabled

// We do not use the com.android.test plugin, because the system restarts the service and it becomes
// inconvenient to debug with tokens.
plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    if (project.isArchFlavorsEnabled) {
        AppConfig.architectures.forEach { arch ->
            if (!arch.isEmulator) {
                if ("arch" !in flavorDimensions) flavorDimensions += "arch"
                productFlavors.create(arch.depArch) {
                    ndk.abiFilters.add(arch.jniArch)
                    dimension = "arch"
                }
            }
        }
    }

    tasks.named("preBuild").dependsOn(
        registerExternalDepsCopyTask()
    )
}

dependencies {
    androidTestImplementation(Dependencies.jna)
    androidTestImplementation(Dependencies.pkcs11jna)
    androidTestImplementation(Dependencies.bouncyCastleBcpkixJdk15on)
    androidTestImplementation(Dependencies.AndroidXTest.jUnit)// FIXME: remove and use kotest
    androidTestImplementation(Dependencies.AndroidXTest.runner)
    androidTestImplementation(Dependencies.Test.kotestAssertionsCore)
    androidTestImplementation(Dependencies.Test.hamcrestLibrary)

    val path = if (File("$rootDir/external").exists()) "$rootDir/external/rtpcsc/java/" else "../../../../pcsc/"
    androidTestImplementation(fileTree(path) { include("rtpcsc*.aar") })

    androidTestImplementation(
        fileTree("../../pkcs11wrapper/lib.pkcs11wrapper-ktx/build/libs/") { include("pkcs11wrapper-ktx*.jar") }
    )
    androidTestImplementation(
        fileTree("../../pkcs11wrapper/lib.pkcs11wrapper/build/libs/") { include("pkcs11wrapper*.jar") }
    )
}

fun registerExternalDepsCopyTask() =
    tasks.register("copyExternalDeps") {
        doLast {
            AppConfig.architectures.forEach { arch ->
                if (!arch.isEmulator)
                    copyJniLibs(project, arch.depArch, arch.jniArch)
            }
        }
    }

fun copyJniLibs(proj: Project, arch: String, jniArch: String) {
    val jniLibs = "${proj.projectDir}/src/main/jniLibs/$jniArch"
    val dependencyArch = "android-$arch"

    copyFromBinaryDeps(rootDir.absolutePath, "pkcs11ecp", dependencyArch, "librtpkcs11ecp.so", jniLibs)
}

tasks.named("clean") {
    doLast {
        delete("src/main/jniLibs")
    }
}