package lushu.Grammar.Grammar

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Grammar(
    private var root: NonTerminal
) {
    data class Result(
        val results: List<NonTerminal.Result>
    )

    fun consume(words: List<String>): Result {
        return Result(listOf(root.consume(words)))
    }

    fun consume(line: String): Result {
        return consume(line.split(tokenSeparator))
    }

    fun consumeLines(lines: String): Result = lines.let {
        Result(
            it.split(logSeparator).fold(listOf<NonTerminal.Result>()) {
                    acc, next ->
                acc + consume(next.split(tokenSeparator)).results
            }
        )
    }

    fun consumeStdin(firstLine: String? = null): Result {
        var results = listOf<NonTerminal.Result>()
        var line = firstLine
        if (line == null) {
            line = readLine()
        }
        while (line != null && !line.isEmpty()) {
            results += consume(line).results
            line = readLine()
        }
        val res = Result(results)
        // System.err.println("Got grammar result: $res")
        return res
    }

    // print pretty-prints the grammar
    fun print(): String {
        return root.print()
    }

    fun statistics(): String {
        val numNonTerminals = root.numNonTerminals()
        val numTerminals = root.numTerminals()
        val numTokens = root.numTokens()
        return "numNonTerminals=$numNonTerminals\n" +
            "numTerminals=$numTerminals\n" +
            "numTokens=$numTokens\n"
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
            if (line == null) {
                line = ""
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
