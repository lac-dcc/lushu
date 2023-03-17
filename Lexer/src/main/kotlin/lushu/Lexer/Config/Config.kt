package lushu.Lexer.Config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import lushu.Lexer.Regex.Interval
import lushu.Lexer.Regex.Node
import lushu.Lexer.Regex.charsetFromS
import org.slf4j.LoggerFactory
import java.io.File

class Config(
    val baseNodes: List<Node>,
    val disjointNodes: List<Pair<Node, Node>>
) {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        fun fromCLIArgs(args: List<String>): Config {
            val configFPath = args[0]
            return fromConfigFile(configFPath)
        }

        fun fromConfigFile(configFPath: String): Config {
            val mapper = ObjectMapper(YAMLFactory())
            mapper.findAndRegisterModules()
            val fileContents = File(configFPath).readText()
            lateinit var yaml: ConfigYAML
            try {
                yaml = mapper.readValue(fileContents, ConfigYAML::class.java)
            } catch (e: MismatchedInputException) {
                logger.error("Failed to parse YAML, assuming empty config. Error: $e")
                return emptyConfig()
            }
            return fromYAML(yaml)
        }

        fun fromYAML(yaml: ConfigYAML): Config {
            logger.debug("Adding nodes from yaml $yaml")
            var baseNodes = listOf<Node>()
            val baseNodeMap = yaml.base
            baseNodeMap.forEach {
                logger.debug("Adding node $it")
                val yamlNode = it.value
                baseNodes += Node(
                    charsetFromS(yamlNode.charset),
                    Interval(yamlNode.intervalMin, yamlNode.intervalMax)
                )
            }
            val disjointNodes = listOf<Pair<Node, Node>>()
            // TODO: disjoint nodes
            return Config(baseNodes, disjointNodes)
        }

        fun emptyConfig(): Config {
            return Config(listOf<Node>(), listOf<Pair<Node, Node>>())
        }
    }

    fun isEmpty(): Boolean {
        // If there are no base nodes, then we have nothing to do, so we
        // consider the given configuration empty.
        return baseNodes.isEmpty()
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is Config -> this.baseNodes == other.baseNodes && this.disjointNodes == other.disjointNodes
        else -> false
    }
}
