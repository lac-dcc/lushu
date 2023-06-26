package lushu.Interceptor.PrintStream

import java.io.OutputStream

class CommandPlainText(
    private val s: String,
    private val ostream: OutputStream
) : Command {
    override fun execute() {
        ostream.write(s.toByteArray())
    }
}
