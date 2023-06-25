package lushu.Interceptor.PrintStream

import java.io.OutputStream

class CommandObfuscate(
    private val s: String,
    private val ostream: OutputStream,
): Command {
    override fun execute() {
        // TODO: use private key to encrypt string
        ostream.write("***".toByteArray())
    }
}
