package lushu.ContextGrammar.Grammar

import java.io.File

class Grammar(private val parser: Parser = Parser()) {
    private fun consume(words: MutableList<String>): String {
        val consumedWords = parser.parsing(words)
        return consumedWords.joinToString(tokenSeparator)
    }

    fun consumeText(text: String): String {
        val words = text.split(" ").toMutableList()
        return consume(words)
    }

    fun consumeStdin(firstLine: String? = null): String {
        var consumed = listOf<String>()
        var line = firstLine
        if (line == null) {
            line = readLine()
        }
        while (!line.isNullOrEmpty()) {
            consumed += consume(line.split(" ").toMutableList())
            line = readLine()
        }
        return consumed.joinToString(logSeparator) + "\n"
    }

    private fun train(words: String?) {
        parser.createRules(words)
    }

    private fun trainText(text: String) {
        parser.createRules(text)
    }

    fun trainStdin(firstLine: String? = null) {
        var line = firstLine
        if (line == null) {
            line = readLine()
        }
        while (!line.isNullOrEmpty()) {
            train(line)
            line = readLine()
        }
        train(line)
    }

    fun trainTextFile(filePath: String) {
        val file = File(filePath)
        val text = file.readText()
        trainText(text)
    }

    companion object {
        private val tokenSeparator = " "
        private val logSeparator = "\n"
        private val grammar: Grammar = Grammar()
        fun trainFromStdin(): Grammar {
            var line = readLine()

            while (line.isNullOrEmpty()) {
                line = readLine()
            }
            grammar.trainStdin(line)
            return grammar
        }

        fun consumeStdin(): Grammar {
            var line = readLine()
            while (line.isNullOrEmpty()) {
                line = readLine()
            }
            println(grammar.consumeStdin(line))
            return grammar
        }

        fun consumeTextFile(filePath: String) {
            val file = File(filePath)
            val lines = file.readLines()

            for (line in lines) {
                println(grammar.consumeText(line))
            }
        }
    }
}
