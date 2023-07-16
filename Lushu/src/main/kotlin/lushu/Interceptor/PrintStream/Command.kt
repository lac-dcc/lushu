package lushu.Interceptor.PrintStream

import lushu.Grammar.Grammar.Grammar

interface Command {
    fun execute()

    companion object {
        // build is a command factory
        fun build(grammarResult: Grammar.Result, state: State): List<Command> {
            return grammarResult.results.fold(listOf<Command>()) { r, result ->
                result.results.fold(r) { rr, terminalResult ->
                    rr + if (terminalResult.sensitive) {
                        CommandObfuscate(terminalResult.word, state.ostream)
                    } else {
                        CommandPlainText(terminalResult.word, state.ostream)
                    } + CommandPlainText(Grammar.tokenSeparator, state.ostream)
                } + CommandPlainText(Grammar.logSeparator, state.ostream)
            }
        }
    }
}
