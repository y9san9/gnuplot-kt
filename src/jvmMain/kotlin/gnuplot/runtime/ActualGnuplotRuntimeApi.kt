package gnuplot.runtime

import gnuplot.GnuplotException
import java.lang.ProcessBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual internal fun expectGnuplotRuntime(
    program: GnuplotProgram,
): GnuplotRuntime {
    return ProcessGnuplotRuntime(program)
}

actual internal suspend fun expectGnuplotProgram(
    name: String,
): GnuplotProgram {
    if (!isExecutable(name)) {
        throw GnuplotException("`$name` is not executable")
    }
    return GnuplotProgram.createUnchecked(name)
}

actual internal suspend fun expectGnuplotProgramOrNull(
    name: String,
): GnuplotProgram? {
    if (!isExecutable(name)) {
        return null
    }
    return GnuplotProgram.createUnchecked(name)
}

private suspend fun isExecutable(command: String): Boolean {
    return runCatching {
        withContext(Dispatchers.IO) {
            val process = ProcessBuilder("command", "-v", command).start()
            val code = process.waitFor()
            return@withContext code == 0
        }
    }.getOrElse {
        false
    }
}

