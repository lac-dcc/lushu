package lushu.ContextGrammar.Grammar

class Grammar(private val parser: Parser = Parser()){
    private fun consume(words: MutableList<String>): String{
        val consumedWords = parser.parsing(words)
        return consumedWords.joinToString(tokenSeparator)
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

    private fun train(words: String?): Unit{
        parser.createRules(words)
    }

    fun trainStdin(firstLine: String? = null): Unit{
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

    companion object{
        private val tokenSeparator = " "
        private val logSeparator = "\n"
        private val grammar: Grammar = Grammar()
        fun fromStdinTrain(): Grammar{
            var line = readLine()

            while (line.isNullOrEmpty()) {
                line = readLine()
            }
            grammar.trainStdin(line)
            return grammar
        }
        fun fromStdin(): Grammar{
            println("Log:")
            var line = readLine()
            while (line.isNullOrEmpty()) {
                line = readLine()
            }
            println(grammar.consumeStdin(line))
            return grammar
        }
    }
}