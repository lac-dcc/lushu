package lushu.ContextGrammar.MapGrammar

import lushu.Merger.Lattice.Node.MergerS
import java.io.File
import java.io.FileReader

class Lushu(
    var mergerConfigFilePath: String = "",
    var outFilePath: String = "",
) {
    private var grammar: Grammar = Grammar()

    init {
        setMergerFile(mergerConfigFilePath)
        setOutputFile(outFilePath)
    }

    private fun setMergerFile(configFilePath: String) {
        if (this.mergerConfigFilePath.isNullOrBlank() && configFilePath.isNullOrBlank()) {
            println(
                "Warning: You must provide the .yaml configuration file during the instantiation of the class.\n" +
                    "For more information, please visit the GitHub repository at (https://github.com/lac-dcc/lushu).\n",
            )
            return
        }
        this.mergerConfigFilePath = configFilePath
        MergerS.load(configFilePath)
    }

    private fun setOutputFile(outFilePath: String) {
        if (this.outFilePath.isNullOrBlank() && outFilePath.isNullOrBlank()) {
            println(
                "Warning: The default output file will be used when functions that involve file printing are utilized.\n" +
                    "For more information, please visit the GitHub repository at (https://github.com/lac-dcc/lushu).\n",
            )
            return
        }
    }

    fun addRules(file: File) = FileReader(file).use { reader ->
        reader.forEachLine {
            addRules(it)
        }
    }

    fun addRules(rule: String) {
        grammar.addContext(rule)
    }

    fun addRules() {
        var rule: String? = null
        if (rule == null) {
            rule = readlnOrNull()
        }
        while (!rule.isNullOrEmpty()) {
            addRules(rule)
            rule = readlnOrNull()
        }
    }

    fun consume(input: String) {
        grammar.consume(input)
    }

    fun consume(file: File) = FileReader(file).use { reader ->
        reader.forEachLine {
            consume(it)
        }
    }

    fun consume() {
        var input: String? = null
        if (input == null) {
            input = readlnOrNull()
        }
        while (!input.isNullOrEmpty()) {
            addRules(input)
            input = readlnOrNull()
        }
    }
}
