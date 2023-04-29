// package lushu.Lushu.LogGenerator

// import org.junit.jupiter.api.Test
// import lushu.Interceptor.PrintStream.LushuPrintStream

// class LogGeneratorTest {
//     @Test
//     fun run() {
//         System.setOut(LushuPrintStream(System.out))
//         val lg = LogGenerator(
//             Paths.get("src", "test", "fixtures", "logs")
//                 .toAbsolutePath().toString()
//         )
//         lg.run(2)
//         System.setOut(PrintStream(System.out))
//     }
// }
