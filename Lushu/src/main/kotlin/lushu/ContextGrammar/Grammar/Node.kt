package lushu.ContextGrammar.Grammar

class Node(
    private val regex: String = "",
    private val sensitive: Boolean = false,
    private val plusCase: Boolean= false,
    private val mergeable: Boolean = false,
    private val children: MutableList<Node> = mutableListOf()) {

    fun getRegex(): String{
        return this.regex
    }
    fun getChildren(): MutableList<Node>{
        return this.children
    }
    fun isSensitive(): Boolean{
        return this.sensitive
    }
    fun isPlusCase(): Boolean{
        return this.plusCase
    }

    fun isMergeable(): Boolean{
        return this.mergeable
    }

    fun match(word: String): Boolean{
        return Regex(this.getRegex()).matches(word)
    }

    fun addChild(r: String, s: Boolean, pc: Boolean, npc: Boolean, m: Boolean): Node{
        this.getChildren().forEach{
            if(it.match(r))
                return it
        }
        this.getChildren().add(Node(r, s, pc, m))

        if(pc && npc)
            return this

        return this.getChildren().last()
    }
}