package lushu.Interceptor.PrintStream

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import lushu.Grammar.Grammar.Grammar
import java.io.OutputStream
import java.io.PrintStream

class LushuPrintStream(
    ostream: OutputStream,
    private val grammar: Grammar,
    private val dispatcher: Dispatcher
) : PrintStream(ostream) {
    private val numListeners = 4
    private val chan = Channel<Job>(Channel.UNLIMITED)
    private val stopChan = Channel<Boolean>(Channel.UNLIMITED)
    private val stoppedChan = Channel<Boolean>(Channel.UNLIMITED)
    private val state = State(ostream)
    private var shouldStop = false

    data class Job(
        val s: String,
        val id: Int = globalID
    ) {
        init {
            globalID++
        }

        companion object {
            private var globalID = 0
        }
    }

    suspend fun start() {
        withContext(Dispatchers.Default) {
            for (i in 0 until numListeners) {
                launch {
                    listen()
                }
            }
            wait()
        }
    }

    private suspend fun listen() {
        while (!(shouldStop && chan.isEmpty)) {
            val job = chan.receive()
            val result = grammar.consumeLines(job.s)
            val cmds = Command.build(result, state)
            dispatcher.queue(job.id, cmds)
        }
    }

    private suspend fun wait() {
        shouldStop = stopChan.receive()
        // TODO: avoid crazy while loop - aholmquist 2023-07-16
        while (!chan.isEmpty) {}
        dispatcher.join()
        stoppedChan.send(true)
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
            withContext(Dispatchers.Default) {
                chan.send(Job(s))
            }
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

    override fun print(o: Any?) {
        this.print(o.toString())
    }
}
