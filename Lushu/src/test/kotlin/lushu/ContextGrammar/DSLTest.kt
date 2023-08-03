package lushu.ContextGrammar.Grammar

import lushu.Test.Utils.Utils
import org.junit.jupiter.api.Test

class DSLTest{
    
    @Test
    fun testRemoveTagsFromWord() {
        
        data class TestCase(
            val desc: String
            val word: String,
            val tags: List<String>
            val expected: String
        )
        
        val testCases = listOf<TestCase>(
            TestCase(
            "empty word",
            "",
            listOf<String>("<s>", "<c>", "</s>"),
            ""
            ),
            TestCase(
            "empty tags",
            "<s>a</s>",
            listOf<String>(),
            "<s>a</s>"
            ),
            TestCase(
            "without tags to remove",
            "a",
            listOf<String>("<s>", "<c>", "</s>"),
            "a"
            ),
            TestCase(
            "with an opening tag to remove before the word",
            "<s>a",
            listOf<String>("<s>", "<c>", "</s>"),
            "a"
            ),
            TestCase(
            "two tags to remove before the word",
            "<s></s>a",
            listOf<String>("<s>", "<c>", "</s>"),
            "a"
            ),
            TestCase(
            "two tags to remove, one before and one after the word",
            "<s><c>a</s>",
            listOf<String>("<s>", "<c>", "</s>"),
            "a"
            ),
            TestCase(
            "word with a double occurrence of the same tag",
            "<s><s>a",
            listOf<String>("<s>", "<c>", "</s>"),
            "a"
            )

        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val dsl = DSL()
            val res = dsl.removeTagsFromWord(it.word, it.tags)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

    @Test
    fun testRemoveAllTagsFromWord() {
        
        data class TestCase(
            val desc: String
            val input: String,
            val expected: String
        )
        
        val testCases = listOf<TestCase>(
            TestCase(
            "empty word",
            "",
            ""
            ),
            TestCase(
            "without tags to remove",
            "a",
            "a"
            ),
            TestCase(
            "with an opening tag to remove before the word",
            "<s>a",
            "a"
            ),
            TestCase(
            "two tags to remove before the word",
            "<s></s>a",
            "a"
            ),
            TestCase(
            "two tags to remove, one before and one after the word",
            "<s><c>a</s>",
            "a"
            ),
            TestCase(
            "word with a double occurrence of the same tag",
            "<s><s>a",
            "a"
            ),
            TestCase(
            "a word initiated with all default tags",
            "<c></c><s></s><*></*><m></m>a",
            "a"
            ),
            TestCase(
            "a word ending with all the standard tags",
            "a<c></c><s></s><*></*><m></m>",
            "a"
            )

        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val dsl = DSL()
            val res = dsl.removeAllTagsFromWord(it.input)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

    @Test
    fun testOpening2CloserTag() {
        
        data class TestCase(
            val desc: String
            val input: String,
            val expected: String
        )
        
        val testCases = listOf<TestCase>(
            TestCase(
            "empty word",
            "",
            "</>"
            ),
            TestCase(
            "word without a starting tag",
            "a",
            "</a>"
            ),
            TestCase(
            "basic case",
            "<s>",
            "</s>"
            ),
            TestCase(
            "a word three letters",
            "<tag>",
            "</tag>"
            ),
            TestCase(
            "multiples '<' and '>'",
            "<s><c>a</s>",
            "</s><c>a</s>"
            ),
            TestCase(
            "a word with < but without '>'",
            "<a",
            "</a>"
            ),
            TestCase(
            "a word with > but without '<'",
            "a>",
            "</a>"
            )

        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val dsl = DSL()
            val res = dsl.opening2CloserTag(it.input)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

    @Test
    fun testOpeningTags2ClosingTags() {
        
        data class TestCase(
            val desc: String
            val input: List<String>,
            val expected: List<String>
        )
        
        val testCases = listOf<TestCase>(
            TestCase(
            "empty list",
            listOf<String>(),
            listOf<String>()
            ),
            TestCase(
            "a list with an empty word",
            listOf<String>(""),
            listOf<String>("</>")
            ),
            TestCase(
            "a list with multiple empty words",
            listOf<String>("", "", ""),
            listOf<String>("</>", "</>", "</>")
            ),
            TestCase(
            "a list with an opening word",
            listOf<String>("c"),
            listOf<String>("</c>")
            ),
            TestCase(
            "a list with multiple opening tags",
            listOf<String>("<c>", "<s>", "<*>"),
            listOf<String>("</c>", "</s>", "</*>"),
            ),
            TestCase(
            "a list with a word containing multiple letters",
            listOf<String>("<tag>"),
            listOf<String>("</tag>")
            ),
            TestCase(
            "a list with multiple word containing multiple letters",
            listOf<String>("<tag>", "<tag1>", "<tag2>"),
            listOf<String>("</tag>", "</tag1>", "</tag2>")
            )

        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val dsl = DSL()
            val res = dsl.openingTags2ClosingTags(it.input)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

    @Test
    fun testHasTags() {
        
        data class TestCase(
            val desc: String
            val input: String,
            val expected: Pair<List<Boolean>, List<Boolean>> 
        )
        
        val testCases = listOf<TestCase>(
            TestCase(
            "empty word",
            "",
            Pair(listOf<Boolean>(false,false,false,false), listOf<Boolean>(false,false,false,false))
            ),
            TestCase(
            "a word without tags",
            "a",
            Pair(listOf<Boolean>(false,false,false,false), listOf<Boolean>(false,false,false,false))
            ),
            TestCase(
            "a opening tag",
            "<s>",
            Pair(listOf<Boolean>(false,true,false,false), listOf<Boolean>(false,true,false,false))
            ),
            TestCase(
            "a closering tag",
            "</s>",
            Pair(listOf<Boolean>(false,false,false,false), listOf<Boolean>(false,false,false,false))
            ),
            TestCase(
            "a sensitive word",
            "<s>sensitive</s>",
            Pair(listOf<Boolean>(false,true,false,false), listOf<Boolean>(false,false,false,false))
            ),
            TestCase(
            "a plus case word",
            "<+>plus</+>",
            Pair(listOf<Boolean>(false,false,true,false), listOf<Boolean>(false,false,false,false))
            ),
            TestCase(
            "a non mergeable word",
            "<+>nonmergeable</+>",
            Pair(listOf<Boolean>(false,false,false,true), listOf<Boolean>(false,false,false,false))
            ),
            TestCase(
            "all opening tags",
            "<c><s><+><m>open",
            Pair(listOf<Boolean>(true, true, true, true), listOf<Boolean>(true, true, true, true))
            ),
            TestCase(
            "all closing tags",
            "closer</c></s></+></m>",
            Pair(listOf<Boolean>(false,false,false,false), listOf<Boolean>(false,false,false,false))
            ),
            TestCase(
            "all tags",
            "<c><s><+><m>all</c></s></+></m>",
            Pair(listOf<Boolean>(true, true, true, true), listOf<Boolean>(false,false,false,false))
            )
        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val dsl = DSL()
            val res = dsl.hasTags(it.input)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

    @Test
    fun testExtractContext() {
        
        data class TestCase(
            val desc: String
            val input: String,
            val expected: List<String>
        )
        
        val testCases = listOf<TestCase>(
            TestCase(
            "empty string",
            "",
            listOf<String>()
            ),
            TestCase(
            "string without context",
            "string without context",
            listOf<String>()
            ),
            TestCase(
            "string with empty context",
            "<c></c>",
            listOf<String>("")
            ),
            TestCase(
            "string with one context",
            "this is a string <c>with context</c>",
            listOf<String>("with context")
            ),
            TestCase(
            "string with two context",
            "this is a <c>context1</c> and this is another <c>context2</c>",
            listOf<String>("context1", "context2")
            )
        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val dsl = DSL()
            val res = dsl.hasTags(it.input)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }
    

}