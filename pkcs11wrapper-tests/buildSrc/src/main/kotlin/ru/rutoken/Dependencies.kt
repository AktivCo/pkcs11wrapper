object Dependencies {
    const val kotlin = "1.6.20"

    const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.2"
    const val bouncyCastleBcpkixJdk15on = "org.bouncycastle:bcpkix-jdk15on:1.70"
    const val bouncyCastleBcprovJdk15on = "org.bouncycastle:bcprov-jdk15on:1.70"
    const val pkcs11jna = "ru.rutoken:pkcs11jna:4.0.0-f192770@jar"
    const val jna = "net.java.dev.jna:jna:5.11.0@aar"

    object Test {
        const val hamcrestLibrary = "org.hamcrest:hamcrest-library:2.2"
        const val kotestAssertionsCore = "io.kotest:kotest-assertions-core:5.2.2"
    }

    object AndroidXTest {
        const val jUnit = "androidx.test.ext:junit:1.1.3"
        const val runner = "androidx.test:runner:1.4.0"
    }
}
