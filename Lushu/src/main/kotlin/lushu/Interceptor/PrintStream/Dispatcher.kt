package lushu.Interceptor.PrintStream

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext

class Dispatcher {
    val chan = Channel<Command>(Channel.UNLIMITED)
    val stopChan = Channel<Boolean>(Channel.UNLIMITED)
    val stoppedChan = Channel<Boolean>(Channel.UNLIMITED)

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    suspend fun start() {
        withContext(Dispatchers.Default) {
            var shouldStop = false
            while (!shouldStop || !chan.isEmpty) {
                select<Unit> {
                    chan.onReceive() {
                        it.execute()
                    }
                    stopChan.onReceive() {
                        shouldStop = true
                    }
                }
            }
            stoppedChan.send(true)
        }
    }

    suspend fun queue(command: Command) {
        withContext(Dispatchers.Default) {
            chan.send(command)
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
}
