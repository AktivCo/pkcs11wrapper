object AppConfig {
    const val compileSdk = 31
    const val minSdk = 21
    const val targetSdk = 31

    val architectures = listOf(
        Architecture("armv7a", "armeabi-v7a", false),
        Architecture("arm64", "arm64-v8a", false),
        Architecture("x86", "x86", true),
        Architecture("x86_64", "x86_64", true)
    )

    data class Architecture(val depArch: String, val jniArch: String, val isEmulator: Boolean)
}
