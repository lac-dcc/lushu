package lushu.Interceptor.PrintStream

import lushu.Grammar.Grammar.Grammar

interface Command {
    fun execute()

    companion object {
        // build is a command factory
        fun build(grammarResult: Grammar.Result, state: State): Command {
            // TODO: other commands -aholmquist 2023-06-11
            return CommandObfuscate(grammarResult.toString(), state.ostream)
        }
    }
}
