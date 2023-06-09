package lushu.Merger.Lattice

import lushu.Merger.Config.Config
import lushu.Test.Utils.Utils
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class NodeFactoryTest {
    private val cfg = Config.fromConfigFile(Utils.basicConfigFullPath())
    private val nf = cfg.nodeFactory

    @Test
    fun topIsTop() {
        assertTrue(NodeFactory.isTop(nf.topNode()))
    }
}
