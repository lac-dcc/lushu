package lushu.LogGenerator

import java.util.concurrent.TimeUnit

// LogGeneratorUnstructured is like LogGenerator, except that the phrases it
// generates do not make sense. All that matters is the format of each word in
// the phrase.
//
// This LogGenerator is good to stress test Lushu, because it makes Lushu
// genereate a very large grammar (the largest possible, indeed!)
class LogGeneratorUnstructured(
    private val maxLogSize: Int = 16,
    private val maxWordSize: Int = 16,
) {
    fun run(numLogs: Int, sleepTime: Long = 0.toLong()) {
        for (i in 0 until numLogs) {
            dispatchLog()
            if (sleepTime > 0) {
                TimeUnit.MILLISECONDS.sleep(sleepTime)
            }
        }
    }

    private fun alpha(): String = "a"

    private fun punct(): String = "."

    private fun randChar(): String = listOf(alpha(), punct()).random()

    private fun wordSep(): String = " "

    private fun dispatchLog() {
        var log = ""
        for (logIdx in 0 until maxLogSize) {
            for (wordIdx in 0 until maxWordSize) {
                log += randChar()
            }
            log += wordSep()
        }
        println(log.substring(0, log.length-1))
    }
}
