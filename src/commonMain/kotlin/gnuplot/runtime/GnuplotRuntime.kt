package gnuplot.runtime

import gnuplot.GnuplotCommand
import gnuplot.GnuplotException

/**
 * This interfaces handles platform-specific details
 * to work with `gnuplot`. There might be separate implementations
 * for JVM, native and Node.js.
 */
public interface GnuplotRuntime {
    /**
     * Executes a list of [GnuplotCommand] or throws [GnuplotException].
     */
    public suspend fun tryExecute(commands: List<GnuplotCommand>): Result<Unit>

    public companion object {
        /**
         * Creates a default platform implementation.
         */
        public fun create(program: GnuplotProgram): GnuplotRuntime {
            return expectGnuplotRuntime(program)
        }

        /**
         * Fetches the default executable from path.
         */
        @Throws(GnuplotException::class)
        public suspend fun default(): GnuplotRuntime {
            val program = GnuplotProgram.default()
            return create(program)
        }

        /**
         * Tries to fetch the default executable from path and
         * returns null if one is unavailable.
         */
        public suspend fun defaultOrNull(): GnuplotRuntime? {
            val program = GnuplotProgram.defaultOrNull() ?: return null
            return create(program)
        }
    }
}
