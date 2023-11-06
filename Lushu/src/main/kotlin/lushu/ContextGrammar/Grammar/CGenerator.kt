package lushu.ContextGrammar.Grammar

import java.io.BufferedWriter
import java.io.File

class CGenerator {
    var tokens = 0
    var fileW: BufferedWriter = File("result.txt").bufferedWriter()

    fun startGenerator(numTokens: Int = 100, filePath: String = "result.txt") {
        var max_tokens = numTokens
        this.fileW = File(filePath).bufferedWriter()
        var tokens = 0
        while (max_tokens > 0) {
            tokens = (300..1000).random()
            this.tokens = max_tokens.coerceAtMost(tokens)
            max_tokens -= this.tokens
            this.fileW.write(generateRandomC())
        }

        fileW.close()
    }

    val ctypes = listOf(
        "int",
        "char",
        "float",
        "double",
        "void",
        "struct",
        "union",
        "enum",
        "typedef",
        "pointer",
        "array",
        "function",
        "macro",
        "preprocessor directive",
        "string",
        "constant",
        "label",
        "variable",
        "operator",
        "keyword",
        "comment",
        "preprocessor symbol",
        "other"
    )

    fun generateRandomType(): String {
        this.tokens -= 1
        return ctypes.random()
    }

    fun generateRandomC(): String {
        val finish = (1..10).random()
        if (tokens <= 3 || finish == 1) {
            val value = (1..2).random()
            if (value == 1) {
                return "return " + generateRandomString(20) + ";"
            }
            return "${generateRandomType()} " + generateRandomString(20) + "=" + (-1000..10000).random() + ";"
        }
        val type = generateRandomType()
        val function = generateRandomString(10)
        val params = generateRandomParams()
        if (tokens <= 5) {
            return "$type $function ($params) {\n${generateRandomString(20)}\n}\n"
        }
        val comp1 = generateRandomC()
        val comp2 = generateRandomC()
        return "$type $function ($params) {\n" +
            "$comp1\n" +
            "}\n" +
            "$comp2"
    }

    fun generateRandomParams(): String {
        val attributes = mutableListOf<String>()
        for (i in 0 until (0..4).random()) {
            val type = generateRandomType()
            val variable = generateRandomString(10)
            val value = (-1000..10000).random()
            attributes.add("$type $variable = $value")
        }
        this.tokens -= attributes.size * 3
        return attributes.joinToString(" ; ")
    }

    fun generateRandomString(length: Int): String {
        val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        this.tokens -= 1
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
