package lushu.ContextGrammar.Grammar

import lushu.ContextGrammar.MapGrammar.MapGrammar
import java.io.File
import java.io.FileReader

class Grammar(
    private val contextAnalyzer: ContextAnalyzer = ContextAnalyzer(),
    private val mapGrammar: MapGrammar = MapGrammar(),
) {
    fun consume(words: MutableList<String>): String {
        val consumedWords = contextAnalyzer.parsing(words)
        return consumedWords.joinToString(tokenSeparator)
    }

    fun consumeHTML() {
        var i = 0
        val result_file = File("result.txt")
        result_file.delete()
        result_file.createNewFile()
        while (i < 20) {
            val randomHTML = HTMLGenerator().startGenerator()
            Thread.sleep(1000)
            i++
            println("Success")
        }
    }

    fun consumeFILE(inFile: String, outFile: String) {
        val file = File(inFile)
        val max = mapGrammar.consume(file, outFile)
        val outfile = File(outFile).bufferedWriter()
        outfile.write(max.toString())
        outfile.close()
    }

    private fun trainMap(string: String?) {
        mapGrammar.addContext(string)
    }

    private fun trainMapFromStdin() {
        var line: String? = null
        if (line == null) {
            line = readLine()
        }
        while (!line.isNullOrEmpty()) {
            trainMap(line)
            line = readLine()
        }
        trainMap(line)
    }

    fun trainMap(file: File): Grammar {
        FileReader(file).use { reader ->
            reader.forEachLine {
                trainMap(it)
            }
        }
        return this
    }

    fun testMap() {
        trainMapFromStdin()
        consumeHTML()
    }

    fun testMap(inFile: String, outFile: String){
        consumeFILE(inFile, outFile)
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
