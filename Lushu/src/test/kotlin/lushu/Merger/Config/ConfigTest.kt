package lushu.Merger.Config

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import lushu.Test.Utils.Utils
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class ConfigTest {
    @Test
    fun configWellFormedShouldNotRaise() {
        Config.fromConfigFile(Utils.basicConfigFullPath())
    }
}
