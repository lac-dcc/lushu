package lushu.ContextGrammar.Grammar

class Parser (private val rules: Rules = Rules()){

    fun parsing(wordsList: MutableList<String>): MutableList<String>{
        return rules.standardize(wordsList)
    }

    fun createRules(wordsList: MutableList<String>): Unit{
        rules.getContext(wordsList)
    }
}