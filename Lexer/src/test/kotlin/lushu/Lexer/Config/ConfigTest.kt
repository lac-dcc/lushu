package lushu.Lexer.Config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import lushu.Lexer.Regex.Node

// Test dependencies
import lushu.Lexer.Regex.testNodeAlphas
import lushu.Lexer.TestUtils.Utils

class ConfigTest {
    @Test
    fun testFromConfigFileEmpty() {
        val configFPath = Utils.configFullPath("configEmpty.yaml")
        val config = Config.fromConfigFile(configFPath)
        assertTrue(config.isEmpty())
    }

    @Test
    fun testFromConfigFileNotEmpty() {
        val configFPath = Utils.configFullPath("configNotEmpty.yaml")
        val config = Config.fromConfigFile(configFPath)
        assertFalse(config.isEmpty())
        val expected = Config(listOf<Node>(testNodeAlphas()), listOf<Pair<Node, Node>>())
        assertEquals(expected, config)
    }
}
