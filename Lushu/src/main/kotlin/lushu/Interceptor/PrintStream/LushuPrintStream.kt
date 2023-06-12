package lushu.Interceptor.PrintStream

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import lushu.Grammar.Grammar.Grammar
import java.io.OutputStream
import java.io.PrintStream

class LushuPrintStream(
    ostream: OutputStream,
    private val grammar: Grammar,
    private val dispatcher: Dispatcher,
) : PrintStream(ostream) {
    val chan = Channel<String>(Channel.UNLIMITED)
    init {
        GlobalScope.launch {
            while (true) {
                val s = chan.receive()
                val result = grammar.consume(s)
                val command = Command(result)
                dispatcher.queue(command)
            }
        }
    }

    override fun print(s: String) {
        GlobalScope.launch {
            chan.send(s)
        }
    }

    override fun print(b: Boolean) {
        this.print(b.toString())
    }

    override fun print(c: Char) {
        this.print(c.toString())
    }

    override fun print(d: Double) {
        this.print(d.toString())
    }

    override fun print(f: Float) {
        this.print(f.toString())
    }

    override fun print(i: Int) {
        this.print(i.toString())
    }

    override fun print(l: Long) {
        this.print(l.toString())
    }
}
