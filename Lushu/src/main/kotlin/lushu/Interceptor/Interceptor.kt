package lushu.Interceptor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lushu.Grammar.Grammar.Grammar
import lushu.Interceptor.PrintStream.Dispatcher
import lushu.Interceptor.PrintStream.LushuPrintStream
import java.io.OutputStream
import java.io.PrintStream

class Interceptor(
    private val ostream: OutputStream,
    private val grammar: Grammar
) {
    private val dispatcher = Dispatcher()
    private val lushuPS = LushuPrintStream(ostream, grammar, dispatcher)

    fun intercept(f: () -> Any?) {
        runBlocking {
            System.setOut(lushuPS)
            val djob = launch(Dispatchers.Default) {
                dispatcher.start()
            }
            val psjob = launch(Dispatchers.Default) {
                lushuPS.start()
            }
            f()
            lushuPS.join()
            djob.cancel()
            psjob.cancel()
            System.setOut(PrintStream(ostream))
        }
    }
}
