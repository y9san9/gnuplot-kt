package gnuplot

public interface GnuplotScope {
    public fun push(command: GnuplotCommand)
}

public fun GnuplotScope.unchecked(string: String) {
    val commands = string.lines().map(GnuplotCommand::createUnchecked)
    for (command in commands) {
        push(command)
    }
}

public fun GnuplotScope.unchecked(block: GnuplotCommand.Builder.() -> Unit) {
    val command = GnuplotCommand.buildUnchecked(block)
    push(command)
}
