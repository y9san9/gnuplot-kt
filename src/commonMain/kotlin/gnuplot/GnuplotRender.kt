package gnuplot

import gnuplot.runtime.GnuplotRuntime

/**
 * Use this function to render plots using gnuplot and high-level
 * wrappers around it. You can use raw api with
 * [gnuplot.runtime.GnuplotRuntime].
 */
public inline suspend fun tryGnuplotRender(
    runtime: GnuplotRuntime? = null,
    block: GnuplotScope.() -> Unit
): Result<Unit> {
    val runtimeOrDefault = runCatching {
        runtime ?: GnuplotRuntime.default()
    }.getOrElse { cause ->
        return Result.failure(cause)
    }
    val scope = GnuplotScopeImpl()
    scope.block()
    val commands = scope.list
    return runtimeOrDefault.tryExecute(commands)
}

/**
 * Use this function to render plots using gnuplot and high-level
 * wrappers around it. You can use raw api with
 * [gnuplot.runtime.GnuplotRuntime].
 */
@Throws(GnuplotException::class)
public inline suspend fun gnuplotRender(
    runtime: GnuplotRuntime? = null,
    block: GnuplotScope.() -> Unit
) {
    val runtimeOrDefault = runtime ?: GnuplotRuntime.default()
    val scope = GnuplotScopeImpl()
    scope.block()
    val commands = scope.list
    runtimeOrDefault.tryExecute(commands).getOrThrow()
}

@PublishedApi
internal class GnuplotScopeImpl : GnuplotScope {
    val list = mutableListOf<GnuplotCommand>()

    override fun push(command: GnuplotCommand) {
        list += command
    }
}
