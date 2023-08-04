package lushu.ContextGrammar.Grammar

import lushu.Test.Utils.Utils
import org.junit.jupiter.api.Test

class NodeTest{

    @Test
    fun testMatch() {
        
        data class NodeInitialization(
            val regex: String
            val sensitive: Boolean,
            val plus: Boolean,
            val nonmergeable: Boolean,
            val children: MutableList<Node> 
        )

        data class TestCase(
            val desc: String,
            val initial: NodeInitialization,
            val input: String,
            val expected: Boolean
        )
        
        val testCases = listOf<TestCase>(
            TestCase(
            "empty regex and empty input",
            NodeInitialization("", false, false, false, mutableListOf<Node>()),
            "",
            true
            ),
            TestCase(
            "empty regex and non-empty input",
            NodeInitialization("", false, false, false, mutableListOf<Node>()),
            "string",
            false
            ),
            TestCase(
            "non-empty regex and empty input",
            NodeInitialization("word", false, false, false, mutableListOf<Node>()),
            "",
            false
            ),
            TestCase(
            "star regex",
            NodeInitialization(".*", false, false, false, mutableListOf<Node>()),
            "4nyth1ng-",
            true
            ),
            TestCase(
            "specific valid regex",
            NodeInitialization("word", false, false, false, mutableListOf<Node>()),
            "word",
            true
            ),
            TestCase(
            "unmatched input",
            NodeInitialization("word", false, false, false, mutableListOf<Node>()),
            "string",
            false
            )
        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val node = Node(it.initial.regex,
                            it.initial.sensitive,
                            it.initial.plus,
                            it.initial.nonmergeable,
                            it.initial.children)
            val res = node.match(it.input)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

    @Test
    fun testFilterMergeblesNodes() {
        
        data class NodeInitialization(
            val regex: String
            val sensitive: Boolean,
            val plus: Boolean,
            val nonmergeable: Boolean,
            val children: MutableList<Node> 
        )

        data class TestCase(
            val desc: String,
            val initial: NodeInitialization,
            val input: Boolean,
            val expected: List<Node>
        )
        
        val testCases = listOf<TestCase>(
            TestCase(
            "empty list of children",
            NodeInitialization("", false, false, false, mutableListOf<Node>()),
            true,
            listOf<Node>()
            ),
            TestCase(
            "list of child nodes without mergeable",
            NodeInitialization("", false, false, false, mutableListOf<Node>(Node(), Node("a", true, true, false))),
            true,
            listOf<Node>()
            ),
            TestCase(
            "list of child nodes with one mergeable",
            NodeInitialization("", false, false, false, mutableListOf<Node>(Node(), Node("a", true, true, false), Node("b", true, true, true))),
            true,
            listOf<Node>(Node("b", true, true, true))
            ),
            TestCase(
            "list of child nodes with two mergeables",
            NodeInitialization("", false, false, false, mutableListOf<Node>(Node(), Node("a", true, true, false), Node("b", true, true, true), Node("c", false, false, true))),
            true,
            listOf<Node>(Node("b", true, true, true), Node("c", false, false, true))
            )
        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val node = Node(it.initial.regex,
                            it.initial.sensitive,
                            it.initial.plus,
                            it.initial.nonmergeable,
                            it.initial.children)
            val res = node.filterMergeblesNodes(it.input)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }

    @Test
    fun testAreNodesEquivalent() {

        data class TestCase(
            val desc: String,
            val node1: Node,
            val node2: Node,
            val expected: Boolean
        )
        
        val testCases = listOf<TestCase>(
            TestCase(
            "equal nodes",
            Node("", false, false, false, mutableListOf<Node>()),
            Node("", false, false, false, mutableListOf<Node>()),
            true
            ),
            TestCase(
            "nodes with different regex",
            Node("a", false, false, false, mutableListOf<Node>()),
            Node("b", false, false, false, mutableListOf<Node>()),
            false
            ),
            TestCase(
            "nodes with different sensitive tags",
            Node("a", true, false, false, mutableListOf<Node>()),
            Node("a", false, false, false, mutableListOf<Node>()),
            false
            ),
            TestCase(
            "nodes with different plus case tags",
            Node("a", true, false, false, mutableListOf<Node>()),
            Node("a", true, true, false, mutableListOf<Node>()),
            false
            ),
            TestCase(
            "nodes with different non-mergeable tags",
            Node("a", true, false, false, mutableListOf<Node>()),
            Node("a", true, false, true, mutableListOf<Node>()),
            false
            ),
            TestCase(
            "equal nodes with differ children",
            Node("", false, false, false, mutableListOf<Node>(Node())),
            Node("", false, false, false, mutableListOf<Node>()),
            true
            ),
            TestCase(
            "a node with a specific regex and a node with a general regex",
            Node(".*", false, false, false, mutableListOf<Node>()),
            Node("a", false, false, false, mutableListOf<Node>()),
            true
            )
        )
        testCases.forEach{
            println("Starting test ${it.desc}")
            val node = Node()
            val res = node.areNodesEquivalent(it.node1, it.node2)
            if (it.expected != res){
                throw Exception(
                    "For test '${it.desc}', expected ${it.expected}, " +
                        "but got $res"
                )
            }
        }
    }
}