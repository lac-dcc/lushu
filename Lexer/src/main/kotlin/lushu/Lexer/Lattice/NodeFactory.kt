package lushu.Lexer.Lattice

import lushu.Lexer.Config.ConfigYAML
import lushu.Lexer.Lattice.Node.Charset
import lushu.Lexer.Lattice.Node.Interval
import lushu.Lexer.Lattice.Node.IntervalNode
import lushu.Lexer.Lattice.Node.Node
import lushu.Lexer.Lattice.Node.PwsetNode
import org.slf4j.LoggerFactory

class NodeFactory(
    yaml: ConfigYAML
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val pwsetNodes = mutableMapOf<Int, PwsetNode>()
    private val charToBaseNodeID = mutableMapOf<Char, Int>()

    private var globalNodeID: Int = 0

    init {
        // First create top node. This ensures that the top node is the one with
        // ID 0.
        buildPwsetNode(setOf<Int>(), Charset(setOf<Char>()), Interval.kleene)

        // Build roof truss of the lattice, which corresponds to the base of the
        // powerset lattice.
        //
        // The YAML config uses name identifiers to describe a node's blacklist,
        // so we temporarily store this name association in a map. Later on, the
        // blacklist must be enforced upon node identifiers, as the powerset
        // lattice is dinamically built.
        val strIDToIntID = mutableMapOf<String, Int>()
        yaml.latticeBase.keys.run {
            var id = 0
            this.forEach {
                strIDToIntID[it] = id
                id++
            }
        }

        yaml.latticeBase.forEach {
            val yamlNode = it.value
            var blacklist = mutableSetOf<Int>()
            if (yamlNode.blacklist != null) {
                yamlNode.blacklist.forEach {
                    blacklist.add(strIDToIntID.getValue(it))
                }
            }
            val pnode = buildPwsetNode(
                blacklist,
                Charset.fromString(yamlNode.charset),
                Interval(yamlNode.intervalMin, yamlNode.intervalMax)
            )
            yamlNode.charset.forEach { c ->
                charToBaseNodeID[c] = pnode.id
            }
        }
    }

    companion object {
        fun topID(): Int = 0
        fun isTop(n: Node): Boolean = n is PwsetNode && n.id == topID()
    }

    fun topNode(): PwsetNode = pwsetNodes.getValue(topID())

    fun joinPwsetNodes(n1: PwsetNode, n2: PwsetNode): PwsetNode {
        if (n1 == n2) {
            // Avoid building another replica node
            return n1
        }
        return buildPwsetNode(
            n1.joinBlacklist(n2),
            n1.joinCharset(n2),
            // For now, we always use kleene interval for powerset nodes that
            // are not part of the roof truss.
            Interval.kleene
        )
    }

    // buildIntervalNode builds a node with one character and interval {1,
    // 1}. Putting this in NodeFactory is necessary so that we attribute the
    // right roof-top node (base node) for the node, from the start. When a node
    // has multiple characters, it become harder to determine it's base node.
    fun buildIntervalNode(c: Char): IntervalNode {
        return IntervalNode(
            pwsetNodes.getValue(charToBaseNodeID.getValue(c)),
            Charset(setOf(c)),
            Interval(1, 1)
        )
    }

    // buildIntervalNodes builds a list of interval nodes from a given string.
    fun buildIntervalNodes(s: String): List<IntervalNode> {
        var ns = listOf<IntervalNode>()
        s.forEach { c ->
            ns += buildIntervalNode(c)
        }
        return ns
    }

    // buildPwsetNode builds a node in the powerset lattice.
    //
    // This function is private because it should only be called directing when
    // building the lattice roof truss (the base nodes). After this
    // initialization, only joins of base nodes should be allowed, but there is
    // another, public, function for that.
    private fun buildPwsetNode(
        blacklist: Set<Int>,
        charset: Charset,
        interval: Interval
    ): PwsetNode {
        val nodeID = globalNodeID
        globalNodeID++
        val newNode = PwsetNode(nodeID, blacklist, charset, interval)
        pwsetNodes[nodeID] = newNode
        return newNode
    }
}
