package lushu.Interceptor.PrintStream

import lushu.Grammar.Grammar.Grammar

interface Command {
    fun execute()

    companion object {
        // build is a command factory
        fun build(grammarResult: Grammar.Result, state: State): List<Command> {
            var cmds = listOf<Command>()
            grammarResult.results.forEach { result ->
                result.results.forEach { subResult ->
                    if (subResult.sensitive) {
                        cmds += CommandObfuscate(subResult.word, state.ostream)
                    } else {
                        cmds += CommandPlainText(subResult.word, state.ostream)
                    }
                    cmds += CommandPlainText(" ", state.ostream)
                }
                cmds += CommandPlainText("\n", state.ostream)
            }
            return cmds
        }
    }
}
