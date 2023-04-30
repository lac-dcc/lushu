package lushu.Lushu.LogGenerator

import lushu.Grammar.Grammar.Grammar
import lushu.Grammar.Grammar.MergerS
import lushu.Interceptor.PrintStream.LushuPrintStream
import lushu.Test.Utils.Utils
import org.junit.jupiter.api.Test
import java.io.PrintStream

class LogGeneratorTest {
    init {
        MergerS.load(Utils.basicConfigFullPath())
    }

    @Test
    fun testLogGeneration() {
        val grammar = Grammar.fromTrainFile(
            Utils.logFullPath("train/user-is-sensitive.log")
        )
        println("Using grammar:\n${grammar.print()}")
        System.setOut(LushuPrintStream(System.out, grammar))
        val lg = LogGenerator(Utils.logGeneratorFullPath())
        lg.run(5)
        System.setOut(PrintStream(System.out))
    }
}
