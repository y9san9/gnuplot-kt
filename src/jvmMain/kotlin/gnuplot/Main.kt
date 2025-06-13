package gnuplot

import gnuplot.runtime.GnuplotProgram
import gnuplot.runtime.ProcessGnuplotRuntime
import java.io.File
import java.lang.ProcessBuilder
import kotlin.random.Random
import kotlin.time.measureTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public suspend fun main() {
    // val log = File("debug-log.txt")
    // val program = GnuplotProgram.default()
    //
    // val runtime = ProcessGnuplotRuntime(program) {
    //     redirectErrorStream(true)
    //         .redirectOutput(ProcessBuilder.Redirect.to(log))
    // }

    val data = List(20) {
        FeelNote(System.currentTimeMillis() - Random.nextInt(1000000000), Random.nextInt(-10, 10))
    }.sortedBy { (timestamp) -> timestamp }

    val output = File("output.png")
    val dataFile = generateDataFile(data)

    try {
        val time = measureTime {
            // gnuplotRender(runtime) {
            gnuplotRender {
                convertToPng()
                intoFile(output)
                plotEmotions(title = "Настроение", input = dataFile)
            }
        }
        println(time)
    } finally {
        dataFile.delete()
    }
}

private fun GnuplotScope.convertToPng() {
    unchecked("set terminal png size 1080,720")
}

private fun GnuplotScope.intoFile(file: File) {
    unchecked { raw("set output"); string(file.absolutePath) }
}

private fun GnuplotScope.plotEmotions(
    title: String,
    input: File,
) {
    unchecked("""
    set style data lines
    set xdata time
    set format x "%d %b\n%H:%M"
    set timefmt "%s"
    """)

    unchecked { raw("set title"); string(title) }
    unchecked {
        raw("plot"); string(input.absolutePath);
        raw("using 1:2 notitle")
    }

}

private data class FeelNote(
    val timestamp: Long,
    val feel: Int,
)

private suspend fun generateDataFile(records: List<FeelNote>): File {
    return withContext(Dispatchers.IO) {
        val tempFile = File.createTempFile("input-", ".dat")
        val string = buildString {
            for ((timestamp, feel) in records) {
                appendLine("${timestamp / 1_000} $feel")
            }
        }
        tempFile.writeText(string)
        return@withContext tempFile
    }
}

