package lushu.LogGenerator

import lushu.Grammar.Grammar.Grammar
import lushu.Grammar.Grammar.MergerS
import lushu.Interceptor.Interceptor
import lushu.Test.Utils.Utils
import org.junit.jupiter.api.Test

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
        val interceptor = Interceptor(System.out, grammar)
        interceptor.intercept()
        val lg = LogGenerator(Utils.logGeneratorFullPath())
        lg.run(5)
        interceptor.passThrough()
    }
}
