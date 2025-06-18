package gnuplot.runtime

expect internal fun expectGnuplotRuntime(program: GnuplotProgram): GnuplotRuntime

expect internal suspend fun expectGnuplotProgram(name: String): GnuplotProgram

expect internal suspend fun expectGnuplotProgramOrNull(name: String): GnuplotProgram?

