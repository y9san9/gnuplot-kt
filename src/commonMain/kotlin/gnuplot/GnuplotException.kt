package gnuplot

/**
 * This error indicates that something wrong happenned to the `gnuplot`
 * executable. It might be that running process was killed, or program was
 * not found in path.
 */
public class GnuplotException(
    override val message: String,
    override val cause: Throwable? = null,
) : RuntimeException()

