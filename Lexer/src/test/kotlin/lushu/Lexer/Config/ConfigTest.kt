package lushu.Lexer.Config

import java.nio.file.Paths

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import lushu.Lexer.Regex.Node
import lushu.Lexer.Regex.Interval

class ConfigTest {
    fun getCurDir(): String {
        return Paths.get("").toAbsolutePath().toString()
    }

    @Test
    fun testFromBaseNodesEmpty() {
        val configFPath = getCurDir() + "/fixtures/configEmpty.yaml"
        val config = Config.fromConfigFile(configFPath)
        assertTrue(config.isEmpty())
    }

    // @Test
    // fun testFromBaseNodesNotEmpty() {
    //     val configFPath = getCurDir() + "/fixtures/configNotEmpty.yaml"
    //     val config = Config.fromConfigFile(configFPath)
    //     assertFalse(config.isEmpty())
    //     val expected = listOf<Node>(Node())
    //     assertEquals(expected, config)
    // }
}
