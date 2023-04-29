package lushu.Grammar

import lushu.Merger.Config.Config
import lushu.Merger.Merger.Token
import lushu.Merger.TestUtils.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TerminalTest {
    private val cfg = Config.fromConfigFile(Utils.basicConfigFullPath())
    private val nf = cfg.nodeFactory

    @Test
    fun testMatchEmpty() {
        val terminal = Terminal(listOf<Token>())
        val expected = listOf<String>()
        val actual = terminal.match(listOf<String>("a"))
        assertEquals(expected, actual)
    }

    // @Test
    // fun testMatchOneToken() {
    //     var tokens: List<Token> = nf.buildIntervalNodes("a")
    //     val terminal = Terminal(tokens)
    //     val expected = "a"
    //     val actual = terminal.toString()
    //     assertEquals(expected, actual)
    // }
}
