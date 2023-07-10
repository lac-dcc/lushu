package lushu.Interceptor.PrintStream

import lushu.Grammar.Grammar.Grammar

interface Command {
    fun execute()

    companion object {
        // build is a command factory
        fun build(grammarResult: Grammar.Result, state: State): List<Command> {
            var cmds = listOf<Command>()
            grammarResult.results.forEach { result ->
                result.results.forEach { terminalResult ->
                    if (terminalResult.sensitive) {
                        cmds += CommandObfuscate(terminalResult.word, state.ostream)
                    } else {
                        cmds += CommandPlainText(terminalResult.word, state.ostream)
                    }
                    cmds += CommandPlainText(" ", state.ostream)
                }
                cmds += CommandPlainText("\n", state.ostream)
            }
            return cmds
        }
    }
}
