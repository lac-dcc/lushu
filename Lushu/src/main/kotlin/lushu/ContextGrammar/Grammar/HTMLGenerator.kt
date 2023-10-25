package lushu.ContextGrammar.Grammar

import java.io.FileWriter

class HTMLGenerator {
    var tokens = 0
    fun startGenerator(numTokens: Int = 100): String {
        this.tokens = numTokens
        return generateRandomHtml()
    }

    val tags = listOf(
        "h1", "h2", "h3", "h4", "h5", "h6",
        "p", "ul", "ol", "li", "table", "tr", "td", "th",
        "img", "a", "input", "form", "script", "style", "body",
        "script", "style", "body",
    )

    fun generateRandomTag(): String {
        this.tokens -= 2
        return tags.random()
    }

    fun generateRandomHtml(): String {
        if (tokens <= 1) {
            return generateRandomString(20)
        }
        val tag = generateRandomTag()
        val attributes = generateRandomAttributes()
        val content = generateRandomContent()
        if (tokens <= 1) {
            return "<$tag $attributes>\n$content\n</$tag>"
        }
        val comp = generateRandomHtml()
        return "<$tag $attributes>\n$content\n$comp\n</$tag>"
    }

    fun generateRandomAttributes(): String {
        val attributes = mutableListOf<String>()
        for (i in 0 until (0..4).random()) {
            val key = generateRandomString(10)
            val value = generateRandomString(10)
            attributes.add("$key=\"$value\"")
        }
        this.tokens -= attributes.size
        return attributes.joinToString(" ")
    }

    fun generateRandomContent(): String {
        val content = when ((0..2).random()) {
            0 -> generateRandomHtml()
            1 -> generateRandomEmail()
            2 -> generateRandomUrl()
            else -> generateRandomString(100)
        }
        this.tokens -= 1
        return content
    }

    fun generateRandomEmail(): String {
        val usernameLength = (5..10).random() // Define o comprimento aleatório do nome de usuário
        val domain = listOf("gmail.com", "yahoo.com", "hotmail.com", "example.com").random()

        val allowedChars = ('a'..'z') + ('A'..'Z')
        val username = (1..usernameLength)
            .map { allowedChars.random() }
            .joinToString("")
        return "$username@$domain"
    }

    fun generateRandomUrl(): String {
        val protocols = listOf("http", "https", "ftp")
        val protocol = protocols.random()

        val domainLength = (5..10).random()
        val allowedChars = ('a'..'z') + ('A'..'Z')
        val domain = (1..domainLength)
            .map { allowedChars.random() }
            .joinToString("")

        val pathLength = (1..5).random()
        val path = (1..pathLength)
            .map { allowedChars.random() }
            .joinToString("/")

        val paramCount = (1..3).random()
        val params = (1..paramCount)
            .map { generateRandomString(5) to generateRandomString(10) }
            .joinToString("") { (key, value) -> "$key=$value" }

        return "$protocol://$domain/$path$params"
    }

    fun generateRandomString(length: Int): String {
        val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
