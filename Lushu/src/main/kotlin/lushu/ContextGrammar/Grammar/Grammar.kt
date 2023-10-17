package lushu.ContextGrammar.Grammar
import java.io.File

class Grammar(private val contextAnalyzer: ContextAnalyzer = ContextAnalyzer()) {
    fun consume(words: MutableList<String>): String {
        val consumedWords = contextAnalyzer.parsing(words)
        return consumedWords.joinToString(tokenSeparator)
    }

    fun doParserFromFile(path: String): String{
        val reader = File(path).bufferedReader()

        val text = reader.readText()

        reader.close()

        val res = DescendentParser().parse(text)
        return res
    }
    fun doParserFromHTMLGenerator(){
        var i = 0
        val result_file = File("result.txt")
        result_file.delete()
        result_file.createNewFile()
        val parser = DescendentParser()
        while(i < 20){
            val randomHTML = HTMLGenerator().generateRandomHtml()
            result_file.appendText(randomHTML)
            parser.parse(randomHTML)
            Thread.sleep(1000)
            i++
        }
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

    fun train(words: String?) {
        contextAnalyzer.createRules(words)
    }

    private fun trainText(text: String) {
        contextAnalyzer.createRules(text)
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
    }
}
