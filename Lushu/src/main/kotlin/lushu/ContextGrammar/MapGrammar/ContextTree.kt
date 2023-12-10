package lushu.ContextGrammar.MapGrammar

import lushu.ContextGrammar.Grammar.Rules
import lushu.Merger.Lattice.Node.GrammarNode
import lushu.Merger.Merger.Merger

class ContextTree(
    val dsl: DSL = DSL(),
    val merger: Merger,
) {
    private val root: GrammarNode = GrammarNode()

    private fun string2list(string: String): List<String> {
        val woNewline = string.split(Grammar.newlineDelim).joinToString(Grammar.spaceDelim)
        return woNewline.split(Grammar.spaceDelim)
    }

    private fun insert(context: MutableList<String>, current: GrammarNode? = root) {
        if (context.isNullOrEmpty()) {
            return
        }

        val firstWord = 0
        val word = context[firstWord]

        val func = dsl.getLambdaFunction(word)
        val mergeable = dsl.isMergeable(word)
        val kleene = dsl.isStarCase(word)

        val wordWoTags = dsl.removeAllTags(word)

        // true if it's the last word in the context
        val endOfContext: Boolean = (context.size == 1)

        val updatedCurrent = current?.findOrAddChild(
            wordWoTags,
            kleene,
            mergeable,
            func,
            endOfContext,
        )

        context.removeAt(firstWord)

        if (nextCases[Rules.starCase]) {
            addContextRule(context, current)
        } else {
            addContextRule(context, updatedCurrent)
        }
    }

    fun insertContext(context: String) {
        val contexts = dsl.extractContext(context)
        val contextTokens = string2list(context)
        insert(contextTokens.toMutableList())
    }
}
