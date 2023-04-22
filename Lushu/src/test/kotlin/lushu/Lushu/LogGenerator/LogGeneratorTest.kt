package lushu.Lushu.LogGenerator

import org.junit.jupiter.api.Test
import java.nio.file.Paths

class LogGeneratorTest {
    @Test
    fun run() {
        System.setOut(CustomPrintStream(System.out))
        val lg = LogGenerator(
            Paths.get("src", "test", "fixtures", "logs")
                .toAbsolutePath().toString()
        )
        lg.run(500)
    }
}
