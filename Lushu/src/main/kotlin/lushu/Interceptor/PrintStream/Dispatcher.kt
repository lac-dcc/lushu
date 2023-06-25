package lushu.Interceptor.PrintStream

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class Dispatcher {
    val chan = Channel<Command>(Channel.UNLIMITED)

    init {
        GlobalScope.launch {
            while (true) {
                val command = chan.receive()
                command.execute()
            }
        }
    }

    fun queue(command: Command) {
        GlobalScope.launch {
            chan.send(command)
        }
    }
}
