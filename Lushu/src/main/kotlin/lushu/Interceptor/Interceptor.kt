package lushu.Interceptor

import java.io.OutputStream
import lushu.Interceptor.PrintStream.LushuPrintStream

class Interceptor(
    private val ostream: OutputStream,
    private val grammar: Grammar,
    private val dispatcher: Dispatcher = Dispatcher(),
) {
    private val lushuPS = LushuPrintStream(ostream, grammar, dispatcher)

    fun intercept() {
        System.setOut(lushuPS)
    }

    fun passThrough() {
        System.setOut(ostream)
    }
}
