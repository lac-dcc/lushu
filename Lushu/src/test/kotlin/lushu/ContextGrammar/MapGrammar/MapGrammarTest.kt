// package lushu.ContextGrammar.MapGrammar

// import org.junit.jupiter.api.Test

// class MapGrammarTest {
//     @Test
//     fun testConsume() {
//         data class TestCase(
//             val desc: String,
//             val input: String,
//             val expected: String
//         )
//         val testCases = listOf<TestCase>(
//             TestCase(
//                 "",
//                 listOf("<c>", "<a>"),
//                 listOf("</c>", "</a>")
//             )
//         )
//         testCases.forEach {
//             println("Starting test ${it.desc}")
//             val dsl = DSL()
//             val actual = dsl.openingTags2ClosingTags(it.openingTags)
//             if (it.expectedClosingTags != actual) {
//                 throw Exception(
//                     "For test '${it.desc}', expected ${it.expectedClosingTags}, " +
//                         "but got $actual"
//                 )
//             }
//         }
//     }
// }
