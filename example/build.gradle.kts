plugins {
    alias(libs.plugins.kotlin.multiplatform)
}


group = "me.y9san9.gnuplot"

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
    commonMainImplementation("me.y9san9.gnuplot:gnuplot-kt:1.0.2")
}

