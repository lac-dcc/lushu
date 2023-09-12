package lushu.ContextGrammar.Grammar

class Script(
    val tagName: String = "script",
    val openingTag: String = "<$tagName>",
    val closerTag: String = "</$tagName>",
    val urls: String = "(http[s]?://.*?)",
    var found: Boolean = false,
    var auxFound: Boolean = false
) {
    fun tags(): List<String>{
        return listOf(openingTag, closerTag)
    }

    fun printURL(url: String) {
        println("URL found: $url")
    }

    fun match(word: String, regex: String): Boolean {
        val r = Regex(regex)
        return r.matches(word)
    }

    fun tagMatcher(word: String) {
        val regex = if (found) {
            closerTag
        } else {
            openingTag
        }

        val res = match(word, regex)
        found = found != res
    }

    fun searchingScriptPattern(word: String) {
        if (found && match(word, this.urls)) {
            printURL(word)
        } else{
            tagMatcher(word)
        }
    }

    fun findMatchingIndex(inputTokens: List<String>): List<Int> {
        var foundIndex = -1

        val result = inputTokens.mapIndexedNotNull { index, token ->
            when {
                token == openingTag -> {
                    auxFound = true
                    foundIndex = index
                    index
                }
                token == closerTag && auxFound -> {
                    auxFound = false
                    index
                }
                auxFound -> index
                else -> null
            }
        }
        return result as List<Int>
    }
}
