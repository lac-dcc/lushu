package lushu.Interceptor.PrintStream

import java.io.OutputStream

class CommandPlainText(
    val s: String,
    val ostream: OutputStream
) : Command {
    override inline fun execute() {
        ostream.write(s.toByteArray())
    }
}
