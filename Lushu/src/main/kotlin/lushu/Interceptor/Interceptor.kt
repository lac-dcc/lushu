package lushu.Interceptor

import lushu.Grammar.Grammar.Grammar
import lushu.Interceptor.PrintStream.LushuPrintStream
import java.io.OutputStream
import java.io.PrintStream

class Interceptor(
    private val ostream: OutputStream,
    private val grammar: Grammar
) {
    private val lushuPS = LushuPrintStream(ostream, grammar)

    fun intercept() {
        System.setOut(lushuPS)
    }

    fun passThrough() {
        System.setOut(PrintStream(ostream))
    }
}
