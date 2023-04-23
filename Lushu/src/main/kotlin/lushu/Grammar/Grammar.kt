package lushu.Grammar

class Grammar(
    private var grammar: NonTerminal = NonTerminal(Terminal(), null)
) {
    fun parse(text: String): List<String> {
        return grammar.match(text.split(" "))
    }

    override fun toString(): String {
        return grammar.toString()
    }
}
