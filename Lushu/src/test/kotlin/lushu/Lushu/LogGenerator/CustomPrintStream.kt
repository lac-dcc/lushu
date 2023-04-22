package lushu.Lushu.LogGenerator

import lushu.Grammar.Grammar
import java.io.OutputStream
import java.io.PrintStream

class CustomPrintStream(
    ostream: OutputStream
) : PrintStream() {
    private val g = Grammar()

    init {
        super(ostream)
    }

    override fun print(s: String) {
        val preffix: String = "> "
        super.print(preffix)
        s = g.conformSensitiveGrammar(s)
        super.print(s)
    }

    override fun print(b: Boolean) {
        this.print(b.toString())
    }

    override fun print(c: Char) {
        this.print(c.toString())
    }

    override fun print(s: Array<Char>) {
        this.print(s.toString())
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

    override fun print(obj: Object) {
        this.print(obj.toString())
    }
}
