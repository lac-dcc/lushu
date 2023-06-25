package lushu.Interceptor

import java.io.OutputStream
import java.io.PrintStream
import lushu.Interceptor.Dispatcher.Dispatcher
import lushu.Interceptor.PrintStream.LushuPrintStream
import lushu.Grammar.Grammar.Grammar

class Interceptor(
    private val ostream: OutputStream,
    private val grammar: Grammar,
    private val dispatcher: Dispatcher = Dispatcher(ostream),
) {
    private val lushuPS = LushuPrintStream(ostream, grammar, dispatcher)

    fun intercept() {
        System.setOut(lushuPS)
    }

    fun passThrough() {
        System.setOut(PrintStream(ostream))
    }
}
