package lushu.Lushu.LogGenerator

import lushu.Interceptor.LushuPrintStream
import org.junit.jupiter.api.Test
import java.io.PrintStream
import java.nio.file.Paths

class LogGeneratorTest {
    @Test
    fun run() {
        System.setOut(LushuPrintStream(System.out))
        val lg = LogGenerator(
            Paths.get("src", "test", "fixtures", "logs")
                .toAbsolutePath().toString()
        )
        lg.run(2)
        System.setOut(PrintStream(System.out))
    }
}
