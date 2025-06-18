package gnuplot.runtime

import gnuplot.GnuplotException
import gnuplot.runtime.expectGnuplotProgram
import gnuplot.runtime.expectGnuplotProgramOrNull

/**
 * This class encapsulates gnuplot program that we don't want
 * to confuse with other types of strings.
 */
@JvmInline
public value class GnuplotProgram private constructor(
    public val string: String,
) {
    public companion object {
        /**
         * Allows to create [GnuplotProgram] without any validation.
         */
        public fun createUnchecked(string: String): GnuplotProgram {
            return GnuplotProgram(string)
        }

        /**
         * Creates gnuplot program by [name] and check if it is executable.
         */
        @Throws(GnuplotException::class)
        public suspend fun create(name: String): GnuplotProgram {
            return expectGnuplotProgram(name)
        }

        /**
         * Creates gnuplot program by [name] and check if it is executable.
         */
        public suspend fun createOrNull(name: String): GnuplotProgram? {
            return expectGnuplotProgramOrNull(name)
        }

        /**
         * Creates a default gnuplot program by the name 'gnuplot'.
         */
        @Throws(GnuplotException::class)
        public suspend fun default(): GnuplotProgram {
            return create("gnuplot")
        }

        /**
         * Creates a default gnuplot program by the name 'gnuplot'.
         */
        public suspend fun defaultOrNull(): GnuplotProgram? {
            return createOrNull("gnuplot")
        }
    }
}
