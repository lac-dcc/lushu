package lushu.Grammar

import lushu.Merger.Config.Config
import lushu.Merger.Lattice.NodeFactory
import lushu.Merger.Merger.Merger

class Grammar(
    configFilePath: String
) {
    val merger: Merger
    val nodeFactory: NodeFactory
    val grammar: NonTerminal = NonTerminal(Terminal(), null)
    val tokenSeparator = " "

    init {
        val config = Config.fromConfigFile(configFilePath)
        nodeFactory = config.nodeFactory
        merger = Merger.fromConfig(config)
    }

    fun matchFromStdin() {
        val input = readLine()
        while (input != null && !input.isEmpty()) {
            val words = input.split(tokenSeparator)
            if (words.isEmpty()) {
                continue
            }
            val notMatched = grammar.match(words)

            // This is an unexpected situation which indicated the program is
            // not behaving as it should. So, as an assertion, we expect the
            // program to stop here if that is the case.
            if (!notMatched.isEmpty()) {
                throw Exception("Grammar was unable to match all the tokens!")
            }
        }
    }

    override fun toString(): String {
        return grammar.toString()
    }
}
