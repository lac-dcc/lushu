package lushu.Grammar.Grammar

import org.junit.Assert.*
import org.junit.Test

class GrammarTest {
  private val grammar = Grammar()

  @Test
  fun testParse() {
    data class TestCase(val desc: String, val s: String, val expected: String)
    val testCases = listOf<TestCase>(
      TestCase("Empty", "", ""),
      TestCase("NonSensitive", "The quick brown fox jumps over the lazy dog", "The quick brown fox jumps over the lazy dog"),
      TestCase("Sensitive", "The user <s>192.158.1.102</s> send a message to email ilovedogs@mail.com", "The user ************* send a message to email ilovedogs@mail.com"),
      TestCase("ManySensitives", "The user <s>192.158.1.102</s> send a ping to <s>192.158.1.14</s>", "The user ************* send a ping to ************"),
      TestCase("EmptySensitive", "<s></s>", ""),
    )
    
    testCases.forEach{
      val grammarParser = grammar.parser(it.s)
      if(it.expected != grammarParser){
        throw Exception("For test '${it.desc}', "
                      + "expected ${it.expected}, "
                      + "but got ${grammarParser}.")
      }
    }

  }

  @Test
  fun testPrint() {
    // TODO: add test cases for print() method
  }
}
