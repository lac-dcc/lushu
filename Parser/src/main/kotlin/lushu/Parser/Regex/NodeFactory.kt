package lushu.ParSy.Regex

import org.slf4j.LoggerFactory

class NodeFactory {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    private val regexRegex = """\[([^\[\]]+)\]((\*)|(\{(\d+),(\d+)\}))""".toRegex()

    fun buildBasicNodes(s: String): List<Node> {
        // Assume the entire string is just plain characters.
        //
        // Example
        // buildBasicNodes("ab") -> [Node([a]{1}), Node([b]{1})]
        //
        // Notice that the formatted string is 6x as long as the
        // non-formatted one.
        var nodes = listOf<Node>()
        s.forEach {
            nodes += Node(setOf(it), Interval(1, 1))
        }
        return nodes
    }

    fun parseNodes(s: String): List<Node> {
        if (s.isEmpty()) {
            return listOf<Node>()
        }
        if (s == dotStar) {
            return listOf(topNode())
        }

        var matchResults = regexRegex.findAll(s)
        if (matchResults.count() == 0) {
            throw Exception("Malformed regex: no matches found in $s")
        }
        var nodes = listOf<Node>()
        matchResults.forEach {
            val matchedGroups = it.groupValues
            val charset = matchedGroups[1]
            val kleeneStar = matchedGroups[3]
            val rangeFirst = matchedGroups[5]
            val rangeSecond = matchedGroups[6]
            if (charset == "") {
                throw Exception("Malformed regex: charset must not be empty")
            }
            if (kleeneStar == "" && (rangeFirst == "" || rangeSecond == "")) {
                throw Exception(
                    "Malformed regex: either kleene star or regex " +
                        "range must be present"
                )
            }
            if (kleeneStar != "") {
                nodes += Node(charsetFromS(charset), Interval(0, 0))
            } else {
                nodes += Node(
                    charsetFromS(charset),
                    Interval(rangeFirst.toInt(), rangeSecond.toInt())
                )
            }
        }
        return nodes
    }

    fun buildString(nodes: List<Node>): String {
        // Special case for top node
        if (nodes.size == 1 && nodes[0].isTop) {
            return dotStar
        }

        var s = ""
        nodes.forEach {
            val cs = it.getCharset()
            val itvl = it.getInterval()
            if (it.isKleene()) {
                s += "[${collapseCharset(cs)}]*"
            } else {
                s += "[${collapseCharset(cs)}]{${itvl.first},${itvl.second}}"
            }
        }
        return s
    }
}
