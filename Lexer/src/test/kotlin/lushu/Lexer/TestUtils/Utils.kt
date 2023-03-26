package lushu.Lexer.TestUtils

import java.nio.file.Paths

class Utils {
    companion object {
        fun getFixturesDir(): String {
            return Paths.get("src", "test", "fixtures").toAbsolutePath().toString()
        }

        fun getFixture(relativePath: String): String {
            return Paths.get(getFixturesDir(), relativePath).toAbsolutePath().toString()
        }

        fun configFullPath(fname: String): String {
            return getFixture(Paths.get("config", fname).toString())
        }

        fun basicConfigFullPath(): String {
            return configFullPath("configBasic.yaml")
        }
    }
}
