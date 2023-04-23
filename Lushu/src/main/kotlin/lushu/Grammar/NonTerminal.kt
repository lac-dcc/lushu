package lushu.Grammar

import org.slf4j.LoggerFactory

class NonTerminal(
    private var first: Node,
    // second is null when it represents a leaf of the grammar
    private var second: Node? = NonTerminal(Terminal(), null)
) : Node {
    private val logger = LoggerFactory.getLogger(this::class.java)

    // Matches the string at the head of the input text list with the tokens in
    // the first node. If a match is found, returns the tail of the input list
    // (the remaining strings after the matched string). Otherwise, creates a
    // new terminal node and tries to match again. If there is a second node,
    // passes the tail of the input list to it for further matching.
    override fun match(input: List<String>): List<String> {
        logger.debug("Matching input $input in non-terminal node")

        val tail = first.match(input)
        if (tail.isEmpty()) {
            logger.debug("Matched first: $tail")
            return tail
        }

        val sec = second
        if (sec == null) {
            logger.debug("No match (second is null)")
            return listOf<String>()
        } else {
            logger.debug("Second is not null")
            return sec.match(tail)
        }
    }

    override fun toString(): String {
        var s = first.toString()
        if (second != null) {
            s += second.toString()
        }
        return s
    }
}
