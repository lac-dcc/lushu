package lushu.Lexer.Regex

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LatticeTest {
    private val psetlat = PowersetLattice(testBaseNodes(), testDisjointNodes())
    private val lattice = Lattice(testBaseNodes(), testDisjointNodes())

    @Test
    fun isNodeWithinBoundsEmptyNode() {
        assertTrue(lattice.isNodeWithinBounds(Node(setOf<Char>(), testNodeInterval1To32())))
    }

    @Test
    fun isNodeWithinBoundsNodeWithOneAlphaRespects() {
        assertTrue(lattice.isNodeWithinBounds(Node(setOf<Char>('a'), testNodeInterval1To32())))
    }

    @Test
    fun isNodeWithinBoundsNodeWithOneNumRespects() {
        assertTrue(lattice.isNodeWithinBounds(Node(setOf<Char>('1'), testNodeInterval1To16())))
    }

    @Test
    fun isNodeWithinBoundsNodeWithOneAlphaExcessive() {
        assertFalse(lattice.isNodeWithinBounds(Node(setOf<Char>('a'), testNodeInterval1To33())))
    }

    @Test
    fun isNodeWithinBoundsNodeWithOneNumExcessive() {
        assertFalse(lattice.isNodeWithinBounds(Node(setOf<Char>('1'), testNodeInterval1To17())))
    }

    @Test
    fun isNodeWithinBoundsNodeWithUnreasonablyWideInterval() {
        assertFalse(lattice.isNodeWithinBounds(Node(setOf<Char>('a'), testNodeInterval0To100())))
    }

    @Test
    fun meetEmptyNodes() {
        val n1 = Node(setOf<Char>(), testNodeInterval1To32())
        val n2 = n1
        val actual = lattice.meet(n1, n2)
        val expected = n1
        assertEquals(expected, actual)
    }

    @Test
    fun meetEmptyWithNonempty() {
        val n1 = Node(setOf<Char>(), testNodeInterval1To32())
        val n2 = Node(setOf<Char>('a'), testNodeInterval1To32())
        val actual = lattice.meet(n1, n2)
        val expected = n2
        assertEquals(expected, actual)
    }

    @Test
    fun meetEmptyWithNonemptyReverseOrder() {
        val n1 = Node(setOf<Char>('a'), testNodeInterval1To32())
        val n2 = Node(setOf<Char>(), testNodeInterval1To32())
        val actual = lattice.meet(n1, n2)
        val expected = n1
        assertEquals(expected, actual)
    }

    @Test
    fun meetEqualNodes() {
        val n1 = Node(setOf<Char>('a'), testNodeInterval1To32())
        val n2 = Node(setOf<Char>('a'), testNodeInterval1To32())
        val actual = lattice.meet(n1, n2)
        val expected = n1
        assertEquals(expected, actual)
    }

    @Test
    fun meetNonEqualNodes() {
        val n1 = Node(setOf<Char>('a'), testNodeInterval1To32())
        val n2 = Node(setOf<Char>('b'), testNodeInterval1To32())
        val actual = lattice.meet(n1, n2)
        val expected = Node(setOf<Char>('a', 'b'), testNodeInterval1To32())
        assertEquals(expected, actual)
    }

    @Test
    fun meetNonCompatibleNodes() {
        val n1 = Node(setOf<Char>('a'), testNodeInterval1To32())
        val n2 = Node(setOf<Char>('.'), testNodeInterval1To32())
        val actual = lattice.meet(n1, n2)
        assertTrue(actual.isTop)
    }

    @Test
    fun meetNonCompatibleNodesReverseOrder() {
        val n1 = Node(setOf<Char>('.'), testNodeInterval1To32())
        val n2 = Node(setOf<Char>('a'), testNodeInterval1To32())
        val actual = lattice.meet(n1, n2)
        assertTrue(actual.isTop)
    }

    @Test
    fun meetKleeneWithNonKleene() {
        val n1 = Node(setOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), kleeneInterval)
        val n2 = Node(setOf<Char>('0'), testNodeInterval1To32())
        val actual = lattice.meet(n1, n2)
        val expected = n1
        assertEquals(expected, actual)
    }

    @Test
    fun meetKleeneWithNonKleeneReverseOrder() {
        val n1 = Node(setOf<Char>('0'), testNodeInterval1To32())
        val n2 = Node(setOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), kleeneInterval)
        val actual = lattice.meet(n1, n2)
        val expected = n2
        assertEquals(expected, actual)
    }

    @Test
    fun disjointBaseNodesSameNodes() {
        val n1 = testNodeAlphasLower()
        val n2 = n1
        assertFalse(psetlat.areDisjoint(n1, n2))
    }

    @Test
    fun disjointBaseNodesDiffNodesNotDisjoint() {
        val n1 = testNodeAlphasLower()
        val n2 = testNodeAlphasUpper()
        assertFalse(psetlat.areDisjoint(n1, n2))
    }

    @Test
    fun disjointBaseNodesDiffNodesDisjointLower() {
        val n1 = testNodeAlphasLower()
        val n2 = testNodePuncts()
        assertTrue(psetlat.areDisjoint(n1, n2))
    }

    @Test
    fun disjointBaseNodesDiffNodesDisjointUpper() {
        val n1 = testNodeAlphasUpper()
        val n2 = testNodePuncts()
        assertTrue(psetlat.areDisjoint(n1, n2))
    }

    @Test
    fun pwsetMeetBaseNodesNotDisjoint() {
        val n1 = testNodeAlphasUpper()
        val n2 = testNodeAlphasLower()
        val actual = psetlat.meet(n1, n2)
        val expected = Node(n1.getCharset().union(n2.getCharset()), kleeneInterval)
        assertEquals(expected, actual)
    }
}
