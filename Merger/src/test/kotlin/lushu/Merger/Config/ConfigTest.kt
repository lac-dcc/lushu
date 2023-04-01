package lushu.Merger.Config

import lushu.Merger.TestUtils.Utils
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.fasterxml.jackson.databind.exc.MismatchedInputException

class ConfigTest {
    @Test
    fun configEmptyShouldRaise() {
        assertThrows(
            MismatchedInputException::class.java,
            { Config.fromConfigFile(Utils.configFullPath("configEmpty.yaml")) },
        )
    }

    @Test
    fun configMalformedShouldRaise() {
        assertThrows(
            MismatchedInputException::class.java,
            { Config.fromConfigFile(Utils.configFullPath("configMissingRequired.yaml")) },
        )
    }

    @Test
    fun configWellFormedShouldNotRaise() {
        Config.fromConfigFile(Utils.basicConfigFullPath())
    }
}
