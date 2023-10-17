package lushu.ContextGrammar.Grammar


import lushu.Merger.Lattice.Node.MergeableToken
import lushu.Merger.Lattice.Node.NonMergeableToken

class DescendentParser {
    private val start_script: NonMergeableToken = NonMergeableToken("<script")
    private val end_script: NonMergeableToken = NonMergeableToken("</script>")
    private val start_body: NonMergeableToken = NonMergeableToken("<body")
    private val end_body: NonMergeableToken = NonMergeableToken("</body>")
    private val URL: MergeableToken = MergeableToken("https://gVrNzsD/r/d/N/EanH46=Dan3nvNeRq")
    private val EMAIL: MergeableToken = MergeableToken("email1@domainname.com")
    private val TOKEN: Regex = """.+""".toRegex()
    private val EMPTY: Regex = Regex("^$")

    private var tokens: MutableList<String> = mutableListOf()
    private var i: Int = 0

    private fun look_ahead(): String {
        if (this.tokens.size > i + 1) {
            return tokens[i + 1]
        } else {
            return empty_string
        }
    }

    private fun next() {
        if (this.tokens.size > this.i + 1) {
            this.i += 1
        }
    }

    private fun ERROR(erro_type: Int = DEFAULT) {
        when(erro_type){
            DEFAULT -> println("ERROR")
            //DONT_MATCH_CASE -> println("String does not match")
            //HTML_SYNTAX_ERROR -> println("HTML document is not well-formed")
        }
    }

    private fun R0() {
        if (start_script.match(look_ahead())) {
            val previous_i = i
            next()
            R1()
            if (end_script.match(look_ahead())) {
                next()
                R0()
            } else {
                setIterator(previous_i)
            }
        }
        if (start_body.match(look_ahead())) {
            val previous_i = i
            next()
            R2()
            if (end_body.match(look_ahead())) {
                next()
                R0()
            } else {
                setIterator(previous_i)
            }
        }
        if (TOKEN.matches(look_ahead())) {
            next()
            R0()
        }
        if (EMPTY.matches(look_ahead())) {
            next()
            return
        }
        ERROR(DONT_MATCH_CASE)
    }

    private fun R1() {
        if (URL.match(look_ahead())) {
            println("R1() -> ${look_ahead()}")
            next()
            R1()
        }
        if (start_script.match(look_ahead())) {
            val previous_i = i
            next()
            R1()
            if (end_script.match(look_ahead())) {
                next()
                R1()
            } else {
                setIterator(previous_i)
            }
        }
        if (start_body.match(look_ahead())) {
            val previous_i = i
            next()
            R3()
            if (end_body.match(look_ahead())) {
                next()
                R1()
            } else {
                setIterator(previous_i)
            }
        }
        if (TOKEN.matches(look_ahead())) {
            if (end_script.match(look_ahead())) {
                return
            }
            next()
            R1()
        }
        if (EMPTY.matches(look_ahead())) {
            return
        }
        ERROR(HTML_SYNTAX_ERROR)
    }

    private fun R2() {
        if (EMAIL.match(look_ahead())) {
            println("R2() -> ${look_ahead()}")
            next()
            R2()
        }
        if (start_body.match(look_ahead())) {
            val previous_i = i
            next()
            R2()
            if (end_body.match(look_ahead())) {
                next()
                R2()
            } else {
                setIterator(previous_i)
            }
        }
        if (start_script.match(look_ahead())) {
            val previous_i = i
            next()
            R3()
            if (end_script.match(look_ahead())) {
                next()
                R2()
            } else {
                setIterator(previous_i)
            }
        }
        if (TOKEN.matches(look_ahead())) {
            if (end_body.match(look_ahead())) {
                return
            }
            next()
            R2()
        }
        if (EMPTY.matches(look_ahead())) {
            return
        }
        ERROR(HTML_SYNTAX_ERROR)
    }

    private fun R3() {
        if (URL.match(look_ahead())) {
            println("R3() -> ${look_ahead()}")
            next()
            R3()
        }
        if (EMAIL.match(look_ahead())) {
            println("R3() -> ${look_ahead()}")
            next()
            R3()
        }
        if (start_body.match(look_ahead())) {
            val previous_i = i
            next()
            R3()
            if (end_body.match(look_ahead())) {
                next()
                R3()
            } else {
                setIterator(previous_i)
            }
        }
        if (start_script.match(look_ahead())) {
            val previous_i = i
            next()
            R3()
            if (end_script.match(look_ahead())) {
                next()
                R3()
            } else {
                setIterator(previous_i)
            }
        }
        if (TOKEN.matches(look_ahead())) {
            if (end_script.match(look_ahead())) {
                return
            } else if (end_body.match(look_ahead())) {
                return
            }
            next()
            R3()
        }
        if (EMPTY.matches(look_ahead())) {

            return
        }
        ERROR(HTML_SYNTAX_ERROR)
    }

    private fun removeSpaces(input: String): String {
        val lines = input.split("\n")
        val cleanedLines = lines.map { it.replace("\\s+".toRegex(), "") }
        return cleanedLines.joinToString("\n")
    }

    private fun stringToList(string: String): List<String> {
        return string.split("\n", " ")
    }

    private fun setTokens(input: String) {
        this.tokens.clear()
        val list = stringToList(input)
        this.tokens = list.toMutableList()
    }

    private fun setIterator(n: Int) {
        if (this.tokens.size > n + 1) {
            this.i = n
        } else {
            this.i = this.tokens.size - 1
        }
    }

    private fun inicialization(input: String) {
        setTokens(input)
        setIterator(eval_start)
    }

    fun parse(input: String): String {
        inicialization(input)
        R0()
        if (this.tokens.size == i + 1) {
            println("Success")
            return this.tokens.joinToString("\n") { it }
        } else {
            println("Fail")
            return input
        }
    }
    companion object {
        val eval_start = -1
        val empty_string = ""
        val DEFAULT = 0
        val DONT_MATCH_CASE = 1
        val HTML_SYNTAX_ERROR = 2
    }
}
