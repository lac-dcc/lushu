package lushu.Lushu.LogGenerator

import lushu.Grammar.Grammar
import java.io.OutputStream
import java.io.PrintStream

class CustomPrintStream(
    ostream: OutputStream
) : PrintStream(ostream) {
    private val g = Grammar()

    override fun print(s: String) {
        val preffix: String = "> "
        super.print(preffix)
        super.print(g.parse(s))
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
