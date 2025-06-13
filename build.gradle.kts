plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    explicitApi()

    compilerOptions {
        extraWarnings = true
        allWarningsAsErrors = true
    }

    jvm()
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines)
}

