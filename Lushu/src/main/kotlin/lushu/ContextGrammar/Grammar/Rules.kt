package lushu.ContextGrammar.Grammar

class Rules(private val root: Node = Node()) {

    var line: MutableList<String> = mutableListOf()
    var auxiliarLine: MutableList<String> = mutableListOf()
    private fun isEndingPlusMatcher(current: Node?, index: Int): Boolean{

        if(line[index].endsWith(logSeparator))
            return true
        else if(current == null){
            return false
        }
        for(child in current!!.getChildren()){
            if(child.match(line[index])){
                if(child.isSensitive())
                    auxiliarLine[index] = "*".repeat(auxiliarLine[index].length)
                return true
            }

        }

        return false
    }
    data class plusMatcherReturn(val i: Int, val node: Node?)
    fun plusMatcher(node: Node?, initialIndex: Int): plusMatcherReturn{
        var current: Node? = node ?: return plusMatcherReturn(1, null)

        for(i in (initialIndex) until line.size){
            if(isEndingPlusMatcher(current, i)){
                line = auxiliarLine.toMutableList()
                return plusMatcherReturn((i - initialIndex - 1), current)
            }

            else if((current == null) || !(current.match(line[i]))){
                auxiliarLine = line.toMutableList()
                return plusMatcherReturn(1, null)
            }

        }
        println("WARNING: Unexpected behavior in plusMatcher function.")
        auxiliarLine = line.toMutableList()
        return plusMatcherReturn(1, null)
    }

    fun matcher(current: Node?, word: String): Node?{
        if(current == null)
            return null
        else if(current!!.getChildren().isNullOrEmpty())
            return root

        for(child in current!!.getChildren()){
            if(child.match(word)){
                return child
            }

        }
        return null
    }

    fun match(node: Node?, initialIndex: Int): Int{

        var current: Node? = node
        var i: Int = initialIndex
        while(i < line.size){
            if(current == root){
                //match
                line = auxiliarLine.toMutableList()
                return (i - initialIndex - 1)
            }else if(current == null ){
                //don't match
                auxiliarLine = line.toMutableList()
                return 1
            }else if(current.isPlusCase()){
                val (nextIndex: Int, nextNode: Node?) = plusMatcher(current, i)
                i += nextIndex
                current = nextNode
                continue
            }else{
                current = matcher(current, line[i])
                if(current != null && current.isSensitive())
                    auxiliarLine[i] = "*".repeat(auxiliarLine[i].length)
            }
            i += 1
        }
        if(current == root){
            //match
            line = auxiliarLine.toMutableList()
            return (line.size - i)
        }else if(current == null ){
            //don't match
            auxiliarLine = line.toMutableList()
            return 1
        } else if((i >= line.size) && (current.getChildren().isNullOrEmpty())){
            //match
            line = auxiliarLine.toMutableList()
            return (line.size - i)
        }
        auxiliarLine = line.toMutableList()
        return 1
    }

    fun standardize(toStandardize: MutableList<String>): MutableList<String>{
        line = toStandardize.toMutableList()
        auxiliarLine = line.toMutableList()
        var current: Node? = root
        var i: Int = 0
        while(i < line.size){
            current = matcher(current, line[i])

            if(current == null){
                i += 1
            }
            else{
                if(current.isSensitive()){
                    auxiliarLine[i] = "*".repeat(auxiliarLine[i].length)
                }

                i += match(current, (i+1))
            }

            current = root
        }
        return line
    }
    fun addRules(contextRule: MutableList<String>){
        var current: Node? = root

        var sensitive: Boolean = false
        var plusCase: Boolean= false
        var mergeable: Boolean = false

        var nextSensitive: Boolean = false
        var nextPlusCase: Boolean= false
        var nextMergeable: Boolean = false

        for(i in 0 until contextRule.size){
            sensitive = nextSensitive
            mergeable = nextMergeable
            plusCase = nextPlusCase

            if(contextRule[i].startsWith("<c>")){
                contextRule.add(i, contextRule[i].substring(sizeInitialFlag, contextRule[i].length))
                contextRule.removeAt(i+1)
            }
            if(contextRule[i].startsWith("<s>")){
                contextRule.add(i, contextRule[i].substring(sizeInitialFlag, contextRule[i].length))
                contextRule.removeAt(i+1)
                sensitive = true
                nextSensitive = true
            }
            if(contextRule[i].startsWith("<+>")){
                contextRule.add(i, contextRule[i].substring(sizeInitialFlag, contextRule[i].length))
                contextRule.removeAt(i+1)
                plusCase = true
                nextPlusCase = true
            }
            if(contextRule[i].startsWith("<t>")){
                contextRule.add(i, contextRule[i].substring(sizeInitialFlag, contextRule[i].length))
                contextRule.removeAt(i+1)
                mergeable = true
                nextMergeable = true
            }
            if(contextRule[i].endsWith("</c>")){
                contextRule.add(i, contextRule[i].substring(0, contextRule[i].length - sizeFinalFlag))
                contextRule.removeAt(i+1)
            }
            if(contextRule[i].endsWith("</s>")){
                contextRule.add(i, contextRule[i].substring(0, contextRule[i].length - sizeFinalFlag))
                contextRule.removeAt(i+1)
                nextSensitive = false
            }
            if(contextRule[i].endsWith("</+>")){
                contextRule.add(i, contextRule[i].substring(0, contextRule[i].length - sizeFinalFlag))
                contextRule.removeAt(i+1)
                nextPlusCase = false
            }
            if(contextRule[i].endsWith("</t>")){
                contextRule.add(i, contextRule[i].substring(0, contextRule[i].length - sizeFinalFlag))
                contextRule.removeAt(i+1)
                nextMergeable = false
            }
            current = current?.addChild(contextRule[i], s = sensitive, pc = plusCase, npc = nextPlusCase, m = mergeable)
        }
    }
    fun getContext(train: MutableList<String>){
        var lineAux: MutableList<String> = mutableListOf()
        var i: Int = 0
        while(i < train.size){
            if(train[i].startsWith("<c>")){
                while(i < train.size){
                    lineAux.add(train[i])
                    if(train[i].endsWith("</c>"))
                        break
                    i += 1
                }
                addRules(lineAux)
            }

            i += 1
        }
    }

    companion object{
        private const val logSeparator = "\n"
        private const val sizeInitialFlag = 3
        private const val sizeFinalFlag = 4
    }

}