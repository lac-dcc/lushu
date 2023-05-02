package lushu.Interceptor.PrintStream

import lushu.Grammar.Grammar.Grammar
import java.io.OutputStream
import java.io.PrintStream

class LushuPrintStream(
    ostream: OutputStream,
    // The grammar is kept for as long as the PrintStream lives. This way, it
    // keeps memory of what it's seen.
    private val grammar: Grammar
) : PrintStream(ostream) {
    override fun print(s: String) {
        val obfuscated = grammar.consume(s)
        super.print(obfuscated)
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
}
