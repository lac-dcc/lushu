package lushu.Merger.Lattice

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import lushu.Merger.Config.Config
import lushu.Merger.TestUtils.Utils

class NodeFactoryTest {
    private val cfg = Config.fromConfigFile(Utils.basicConfigFullPath())
    private val nf = cfg.nodeFactory

    @Test
    fun topIsTop() {
        assertTrue(NodeFactory.isTop(nf.topNode()))
    }
}
