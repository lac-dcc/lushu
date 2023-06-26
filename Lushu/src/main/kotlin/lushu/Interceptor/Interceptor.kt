package lushu.Interceptor

import lushu.Grammar.Grammar.Grammar
import lushu.Interceptor.PrintStream.Dispatcher
import lushu.Interceptor.PrintStream.LushuPrintStream
import java.io.OutputStream
import java.io.PrintStream
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.Dispatchers

class Interceptor(
    private val ostream: OutputStream,
    private val grammar: Grammar
) {
    private val dispatcher = Dispatcher()
    private val lushuPS = LushuPrintStream(ostream, grammar, dispatcher)

    fun intercept(f: () -> Any?) {
        runBlocking {
            System.err.println("Starting interception")
            System.setOut(lushuPS)
            val djob = launch(Dispatchers.Default) {
                dispatcher.start()
            }
            val psjob = launch(Dispatchers.Default) {
                lushuPS.start()
            }
            System.err.println("Starting f")
            f()
            lushuPS.join()
            djob.cancel()
            psjob.cancel()
            System.err.println("Finished f")
            System.setOut(PrintStream(ostream))
            System.err.println("Finished interception")
        }
    }
}
