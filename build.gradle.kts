import org.gradle.kotlin.dsl.registering

tasks {
    val printVersion by registering {
        group = "CI"

        doFirst {
            println(libs.versions.gnuplot.get())
        }
    }
}
