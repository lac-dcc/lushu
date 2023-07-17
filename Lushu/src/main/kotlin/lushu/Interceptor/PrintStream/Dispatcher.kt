package lushu.Interceptor.PrintStream

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class Dispatcher {
    private val chan = Channel<Pair<Int, List<Command>>>(Channel.UNLIMITED)
    private val stoppedChan = Channel<Boolean>(Channel.UNLIMITED)

    suspend fun start() = withContext(Dispatchers.Default) {
        listen()
    }

    private suspend fun listen() {
        var curJobID = 0
        var pickedUpCommands = mutableMapOf<Int, List<Command>>()
        var shouldStop = false
        while (!(shouldStop && chan.isEmpty && pickedUpCommands.isEmpty())) {
            // Check if job we are looking for has already been picked up
            // previously.
            if (pickedUpCommands.containsKey(curJobID)) {
                val jobID = curJobID
                val cmds = pickedUpCommands.getValue(jobID)

                cmds.forEach { it.execute() }
                curJobID++

                pickedUpCommands.remove(jobID)
                continue
            }

            // Otherwise, pick a new job from the client
            val job = chan.receive()
            val jobID = job.first
            val cmds = job.second
            if (cmds.isEmpty()) {
                shouldStop = true
                continue
            }

            // Add job for later processing, ignore it for now, will get to it
            // at some point.
            if (jobID != curJobID) {
                pickedUpCommands[jobID] = cmds
                continue
            }

            cmds.forEach { it.execute() }
            curJobID++
        }
        stoppedChan.send(true)
    }

    suspend fun queue(jobID: Int, commands: List<Command>) {
        withContext(Dispatchers.Default) {
            chan.send(Pair(jobID, commands))
        }
    }

    fun join() {
        runBlocking {
            withContext(Dispatchers.Default) {
                // TODO: use something like the stopChan in LushuPrintStream
                // instead of signaling end with empty list -aholmquist 2023-07-16
                chan.send(Pair<Int, List<Command>>(0, listOf()))
                stoppedChan.receive()
            }
        }
    }
}
