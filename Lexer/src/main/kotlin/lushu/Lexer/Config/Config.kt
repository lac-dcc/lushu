package lushu.Lexer.Config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import lushu.Lexer.Lattice.NodeFactory
import org.slf4j.LoggerFactory
import java.io.File

class Config(
    val nodeFactory: NodeFactory
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
                logger.error("Failed to parse config $configFPath: $e")
                throw e
            }
            return fromYAML(yaml)
        }

        fun fromYAML(yaml: ConfigYAML): Config {
            return Config(NodeFactory(yaml))
        }
    }
}
