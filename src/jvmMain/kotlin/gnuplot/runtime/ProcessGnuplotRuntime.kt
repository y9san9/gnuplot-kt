package gnuplot.runtime

import gnuplot.GnuplotCommand
import gnuplot.GnuplotException
import java.lang.Process
import java.lang.ProcessBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public class ProcessGnuplotRuntime(
    private val program: GnuplotProgram,
    private val builder: Builder = Builder { this },
) : GnuplotRuntime {
    @Throws(GnuplotException::class)
    override suspend fun tryExecute(
        commands: List<GnuplotCommand>,
    ): Result<Unit> {
        return runCatching {
            execute(commands)
        }.recoverCatching { cause ->
            throw GnuplotException("Failed to execute gnuplot.", cause)
        }
    }

    private suspend fun execute(commands: List<GnuplotCommand>) {
        val string = commands.joinToString(
            separator = ";" + System.lineSeparator(),
        ) { command -> command.string }

        return withContext(Dispatchers.IO) {
            val process = ProcessBuilder(program.string, "-e", string)
                .let { process ->
                    with (builder) {
                        process.apply(commands)
                    }
                }.start()

            val code = process.waitFor()
            if (code != 0) {
                error("Non-zero exit code from gnuplot")
            }
        }
    }

    public fun interface Builder {
        public fun ProcessBuilder.apply(
            commands: List<GnuplotCommand>
        ): ProcessBuilder
    }
}

public suspend fun main() {
    val runtime = GnuplotRuntime.default()
    val commands = GnuplotCommand.listOfUnchecked("plot sin(x)")
    val result = runtime.tryExecute(commands)
    println(result.getOrThrow())
}
