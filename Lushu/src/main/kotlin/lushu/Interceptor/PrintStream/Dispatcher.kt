package lushu.Interceptor.PrintStream

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class Dispatcher {
    val chan = Channel<Command?>(Channel.UNLIMITED)
    val stoppedChan = Channel<Boolean>(Channel.UNLIMITED)

    suspend fun start() = withContext(Dispatchers.Default) {
        var shouldStop = false
        while (!(shouldStop && chan.isEmpty)) {
            val cmd = chan.receive()
            if (cmd == null) {
                shouldStop = true
            } else {
                cmd.execute()
            }
        }
        stoppedChan.send(true)
    }

    suspend fun queue(command: Command) {
        withContext(Dispatchers.Default) {
            chan.send(command)
        }
    }

    fun join() {
        runBlocking {
            withContext(Dispatchers.Default) {
                chan.send(null)
                stoppedChan.receive()
            }
        }
    }
}
