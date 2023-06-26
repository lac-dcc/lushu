package lushu.Interceptor.PrintStream

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext
import lushu.Grammar.Grammar.Grammar
import java.io.OutputStream
import java.io.PrintStream

class LushuPrintStream(
    ostream: OutputStream,
    private val grammar: Grammar,
    private val dispatcher: Dispatcher
) : PrintStream(ostream) {
    val chan = Channel<String>(Channel.UNLIMITED)
    val stopChan = Channel<Boolean>(Channel.UNLIMITED)
    val stoppedChan = Channel<Boolean>(Channel.UNLIMITED)
    private val state = State(ostream)

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    suspend fun start() {
        withContext(Dispatchers.Default) {
            var shouldStop = false
            while (!shouldStop || !chan.isEmpty) {
                select<Unit> {
                    chan.onReceive() {
                        val result = grammar.consume(it)
                        val cmds = Command.build(result, state)
                        System.err.println("Dispatching commands")
                        cmds.forEach { dispatcher.queue(it) }
                    }
                    stopChan.onReceive() {
                        System.err.println("Received stop command in print stream")
                        shouldStop = true
                    }
                }
            }
            dispatcher.join()
            stoppedChan.send(true)
        }
    }

    fun join() {
        runBlocking {
            withContext(Dispatchers.Default) {
                stopChan.send(true)
                stoppedChan.receive()
            }
        }
    }

    override fun print(s: String) {
        runBlocking {
            launch {
                chan.send(s)
            }
        }
    }

    override fun print(o: Any?) {
        this.print(o.toString())
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
