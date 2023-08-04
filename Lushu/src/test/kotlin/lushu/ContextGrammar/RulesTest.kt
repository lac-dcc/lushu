package lushu.ContextGrammar.Grammar

import lushu.Test.Utils.Utils
import org.junit.jupiter.api.Test
import lushu.ContextGrammar.Grammar.Node

class RulesTest{

    @Test
    fun testRemoveTagsFromWord() {

        data class TestCase(
            val desc: String,
            val word: String,
            val current: Node,
            val expected: Boolean
        )
        val testCases = listOf<TestCase>(
            TestCase(
            "empty word and empty ending regex",
            "",
            Node("a", false, false, false, mutableListOf<Node>(Node(""))),
            true
            ),
            TestCase(
            "unmatched regex",
            "a",
            Node("a", false, false, false, mutableListOf<Node>(Node("b"))),
            false
            ),
            TestCase(
            "current node without children",
            "a",
            Node("a", false, false, false, mutableListOf<Node>()),
            false
            )
        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val rules = Rules()
            val res = rules.isEndingPlusCase(it.word, it.current)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

    @Test
    fun testPlusCaseMatcher() {

        data class TestCase(
            val desc: String,
            val inputTokens: MutableList<String>,
            val mutableTokens: MutableList<String>,
            val index: Int,
            val current: Node?,
            val expected: Pair<Int, Node?>
        )
        val testCases = listOf<TestCase>(
            TestCase(
            "index greater than input tokens size",
            mutableListOf<String>("a"),
            mutableListOf<String>("a"),
            2,
            Node(),
            Pair(1, null)
            ),
            TestCase(
            "current node is null",
            mutableListOf<String>("a"),
            mutableListOf<String>("a"),
            0,
            null,
            Pair(1, null)
            ),
            TestCase(
            "is ending plus case true",
            mutableListOf<String>("a"),
            mutableListOf<String>("a"),
            0,
            Node("b", false, false, false, mutableListOf<Node>(Node("a"))),
            Pair(1, Node("a"))
            )
        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val rules = Rules()
            val res = rules.plusCaseMatcher(it.inputTokens,
                                            it.mutableTokens,
                                            it.index,
                                            it.current)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

    @Test
    fun testMatcher() {

        data class TestCase(
            val desc: String,
            val current: Node?,
            val word: String,
            val expected: Node?
        )

        val testCases = listOf<TestCase>(
            TestCase(
            "current node is null",
            null,
            "",
            null
            ),
            TestCase(
            "child with empty regex and empty word",
            Node("", false, false, false, mutableListOf<Node>(Node("")))
            "",
            Node("")
            ),
            TestCase(
            "unmatched",
            Node("", false, false, false, mutableListOf<Node>(Node()))
            "a",
            null
            ),
            TestCase(
            "matched",
            Node("", false, false, false, mutableListOf<Node>(Node(".*")))
            "4nyth1ng-",
            Node(".*")
            )
        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val rules = Rules()
            val res = rules.matcher(it.current,
                                    it.word)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

    @Test
    fun testMatchTokensAgainstPatternContext() {

        data class TestCase(
            val desc: String,
            val inputTokens: MutableList<String>,
            val mutableTokens: MutableList<String>,
            val index: Int,
            val current: Node?,
            val expected: Int
        )
        val testCases = listOf<TestCase>(
            TestCase(
            "current node null",
            mutableListOf<String>("a"),
            mutableListOf<String>("a"),
            2,
            null,
            1
            ),
            TestCase(
            "index greater then input tokens size and current children is null",
            mutableListOf<String>("a"),
            mutableListOf<String>("a"),
            2,
            Node(),
            -2
            ),
            TestCase(
            "index greater then input tokens size and current children isn't null",
            mutableListOf<String>("a"),
            mutableListOf<String>("a"),
            2,
            Node("a", false, false, false, mutableListOf<Node>(Node("b"))),
            1
            )
        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val rules = Rules()
            val res = rules.matchTokensAgainstPatternContext(it.inputTokens,
                                                             it.mutableTokens,
                                                             it.index,
                                                             it.current)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

    @Test
    fun findMatchingIndex() {

        data class TestCase(
            val desc: String,
            val inputTokens: List<String>,
            val regexList: List<String>,
            val expected: List<Int>
        )
        val testCases = listOf<TestCase>(
            TestCase(
            "empty lists",
            listOf<String>(),
            listOf<String>(),
            listOf<Int>()
            ),
            TestCase(
            "empty inputTokens lists",
            listOf<String>(),
            listOf<String>("a", "b", "c"),
            listOf<Int>()
            ),
            TestCase(
            "empty regex lists",
            listOf<String>("a", "b", "c"),
            listOf<String>(),
            listOf<Int>()
            ),
            TestCase(
            "star regex",
            listOf<String>("a", "1", "-", "4nyth1ng-"),
            listOf<String>(".*"),
            listOf<Int>(0, 1, 2, 3)
            )
        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val rules = Rules()
            val res = rules.findMatchingIndex(it.inputTokens,
                                              it.regexList)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

}