package lushu.Grammar.Grammar

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Grammar(
    private var root: NonTerminal
) {
    fun consume(words: List<String>): String {
        val consumedWords = root.consume(words)
        return consumedWords.joinToString(tokenSeparator)
    }

    fun consume(s: String): String {
        return consume(s.split(tokenSeparator))
    }

    fun consumeStdin(firstLine: String? = null): String {
        var consumed = listOf<String>()
        var line = firstLine
        if (line == null) {
            line = readLine()
        }
        while (line != null && !line.isEmpty()) {
            consumed += consume(line)
            line = readLine()
        }
        return consumed.joinToString(logSeparator) + "\n"
    }

    // print pretty-prints the grammar
    fun print(): String {
        return root.print()
    }

    override fun toString(): String {
        return "Grammar(root=$root)"
    }

    companion object {
        private val tokenSeparator = " "
        private val logSeparator = "\n"

        fun fromTrainFile(trainFile: String): Grammar {
            println("Training Lushu Grammar with file '$trainFile'")
            println("----------------------------------------")
            val reader = BufferedReader(FileReader(File(trainFile)))
            var line = reader.readLine()
            while (line == null || line.isEmpty()) {
                line = reader.readLine()
            }
            val grammar = Grammar(nonTerminalFromLine(line))
            while (line != null && !line.isEmpty()) {
                println("Training with log: $line")
                grammar.consume(line)
                line = reader.readLine()
            }
            println("----------------------------------------")
            println("Finished training grammar\n")
            return grammar
        }

        // fromStdin starts a new grammar from scratch. It reads every line
        // received from stdin, consuming every line.
        //
        // This is not very useful for interception, since usually the intercepted
        // strings will come the program itself inside the JVM.
        fun fromStdin(): Grammar {
            var line = readLine()
            while (line == null || line.isEmpty()) {
                line = readLine()
            }
            val grammar = Grammar(nonTerminalFromLine(line))
            grammar.consumeStdin(line)
            return grammar
        }

        fun fromLine(line: String): Grammar {
            return Grammar(nonTerminalFromLine(line))
        }

        private fun nonTerminalFromLine(line: String): NonTerminal {
            return NonTerminal.new(line.split(tokenSeparator)[0])
        }
    }
}
