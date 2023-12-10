package lushu.ContextGrammar.MapGrammar

import lushu.Merger.Lattice.Node.MergerS
import lushu.Merger.Merger.Merger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileReader

class Grammar() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val dsl: DSL = DSL()
    private val merger: Merger = MergerS.merger()

    private val contextMap: ContextMap = ContextMap(dsl, merger)
    private val contextTree: ContextTree = ContextTree(dsl, merger)

    fun consume(file: File) = FileReader(file).use { reader ->
        reader.forEachLine {
            consume(it)
        }
    }

    private fun streamString(input: String) =
        blankRegex.findAll(input).forEach { matchResult ->
            contextMap.match(matchResult.value)
            contextTree.match(matchResult.value)
        }

    fun consume(input: String) = streamString(input)

    fun addContext(contextInput: String?) {
        if (contextInput.isNullOrBlank()) {
            return
        }
        if (contextInput.matches(mapContextRegex)) {
            contextMap.insertContext(contextInput)
        } else {
            contextTree.insertContext(contextInput)
        }
    }

    companion object {
        val minElems = 3
        val noOccurrences = 0
        val spaceDelim = " "
        val newlineDelim = "\n"

        val contextRegex = Regex("""<c>(.*?)</c>""")
        val mapContextRegex = Regex("""<c>\S+\s*(<\*>\\S+<\/\*>\s*)+\\S+\s*<\/c>""")
        val blankRegex = Regex("""\S+""")
    }
}
