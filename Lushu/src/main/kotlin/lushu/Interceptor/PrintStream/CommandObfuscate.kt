package lushu.Interceptor.PrintStream

import java.io.OutputStream

class CommandObfuscate(
    val s: String,
    val ostream: OutputStream
) : Command {
    override inline fun execute() {
        // TODO: use private key to encrypt string
        ostream.write("***".toByteArray())
    }
}
