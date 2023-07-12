package lushu.ContextGrammar.Grammar

class Parser (private val rules: Rules = Rules()){

    fun parsing(wordsList: MutableList<String>): List<String>{
        return rules.tokens2CipherTokens(wordsList)
    }

    fun createRules(words: String?){
        rules.getContext(words)
    }
}