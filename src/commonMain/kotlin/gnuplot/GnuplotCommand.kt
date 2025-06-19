package gnuplot

/**
 * This class encapsulates a command for gnuplot that we don't want
 * to confuse with other types of strings.
 */
@JvmInline
public value class GnuplotCommand private constructor(
    public val string: String,
) {
    public class Builder {
        public val stringBuilder: StringBuilder = StringBuilder()
    }

    public companion object {
        /**
         * Allows to create [GnuplotCommand] without any validation.
         * We don't know any ways to validate gnuplot command at the moment,
         * so this is the only way one can create this type.
         *
         * In the future we might add some safe factory-functions as well.
         */
        public fun createUnchecked(string: String): GnuplotCommand {
            return GnuplotCommand(string)
        }

        /**
         * Creates a list of [GnuplotCommand]s. Used to reduce boilerplate
         * when creating such a list.
         */
        public fun listOfUnchecked(
            vararg string: String,
        ): List<GnuplotCommand> {
            return string.map(::createUnchecked)
        }

        /**
         * Create gnuplot command with some helpers for ints, strings, etc.
         */
        public inline fun buildUnchecked(
            block: Builder.() -> Unit,
        ): GnuplotCommand {
            val builder = Builder()
            builder.block()
            return createUnchecked(builder.stringBuilder.toString())
        }
    }
}

public fun GnuplotCommand.Builder.ensureSpace() {
    if (stringBuilder.length == 0) return
    if (stringBuilder.last().isWhitespace()) return
    stringBuilder.append(' ')
}

public fun GnuplotCommand.Builder.raw(string: String) {
    ensureSpace()
    stringBuilder.append(string)
}

public fun GnuplotCommand.Builder.string(string: String) {
    ensureSpace()
    stringBuilder.append('"')
    stringBuilder.append(string.replace("\"", "\\\""))
    stringBuilder.append('"')
}
