import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.maven.publish)
}

group = "me.y9san9.gnuplot"
version = libs.versions.gnuplot.get()

kotlin {
    explicitApi()

    jvmToolchain(21)

    // compilerOptions {
    //     extraWarnings = true
    //     allWarningsAsErrors = true
    // }

    jvm()
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)

    pom {
        name = "gnuplot"
        description = "Simple Kotlin Wrapper around CLI-tool gnuplot"
        url = "https://github.com/y9san9/gnuplot"

        licenses {
            license {
                name = "MIT"
                distribution = "repo"
                url = "https://github.com/y9san9/gnuplot/blob/main/LICENSE.md"
            }
        }

        developers {
            developer {
                id = "y9san9"
                name = "Alex Sokol"
                email = "y9san9@gmail.com"
            }
        }

        scm {
            connection ="scm:git:ssh://github.com/y9san9/gnuplot.git"
            developerConnection = "scm:git:ssh://github.com/y9san9/gnuplot.git"
            url = "https://github.com/y9san9/gnuplot"
        }
    }

    signAllPublications()
}
