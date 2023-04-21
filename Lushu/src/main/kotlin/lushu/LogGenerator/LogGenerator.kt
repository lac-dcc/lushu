package lushu.LogGenerator

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import kotlin.collections.List

class LogGenerator(
  private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  private var ACTIONS: List<String>
  private var MESSAGES: List<String>
  private var FILE_NAMES: List<String>
  private var CONTENTS: List<String>
  private var NAMES: List<String>

) {

  fun getLines(file: String): List<String> {
    var lines = Files.readAllLines(Paths.get(file))
    return lines
  }

  fun setMessages() {
    ACTIONS = getLines("")
    MESSAGES = getLines("")
    FILE_NAMES = getLines("")
    CONTENTS = getLines("")
    NAMES = getLines("")
  }

  fun generateDate(random: Random): String {
    val year = random.nextInt(2) + 2023
    val month = random.nextInt(12)
    val day = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + 1

    calendar.set(year, month, day)

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

  fun generateHours(random: Random): String {
   val hour = random.nextInt(24)
    val minute = random.nextInt(60)

    val formattedHour = String.format("%02d:%02d", hour, minute)
    return formattedHour
  }

  fun generateCPF(random: Random): String {
    var cpf = ""
    for (i in 0 until 3) {
      for (j in 0 until 3) {
        cpf += String.format("%d", random.nextInt(10))
      }
      if (i != 2) {
        cpf += "."
      }
    }
    cpf += String.format("-%d%d", random.nextInt(10), random.nextInt(10))
    return cpf
  }

  fun generateRandomId(): String {
    val validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    val sb = StringBuilder()
    val random = Random()
    for (i in 0 until 7) {
      sb.append(validChars[random.nextInt(validChars.length)])
    }
    return sb.toString()
  }

  @Throws(InterruptedException::class, IOException::class)
  private fun grammarPrepare(random: Random) {
    for (i in 0 until 100) {
        val cpf = generateCPF(random)
        val action = ACTIONS[random.nextInt(ACTIONS.size)]
        val message = MESSAGES[random.nextInt(MESSAGES.size)]
        val to_user = generateCPF(random)
        val id = generateRandomId()
        val fileName = FILE_NAMES[random.nextInt(FILE_NAMES.size)]
        val content = CONTENTS[random.nextInt(CONTENTS.size)]
        val name = NAMES[random.nextInt(NAMES.size)]
        val amount = "$" + (random.nextInt(99901) + 10) * 100 + ",00"
        val date = generateDate(random)
        val hours = generateHours(random)

        var updatedMessage = message
            .replace("{CUSTOMER_ID}", id)
            .replace("{TIME}", hours)
            .replace("{DATE}", date)
            .replace("{AMOUNT}", amount)
            .replace("{USER_NAME}", name)
            .replace("{SENDER_NAME}", name)
            .replace("{CUSTOMER_NAME}", name)
            .replace("{CPF}", "<s>$cpf</s>")
            .replace("{CONTENT}", content)
            .replace("{FILE_NAME}", fileName)
            .replace("{ID}", id)
            .replace("{TO_USER}", "<s>$to_user</s>")
            .replace("{ACTION}", action)
            .replace("{DATETIME}", DATE_FORMAT.format(Date()))

        println(updatedMessage)
    }
  }

  @Throws(IOException::class, InterruptedException::class)
  private fun printMessages(random: Random, n: Int) {
    for (i in 0 until n) {
        val cpf = generateCPF(random)
        val action = ACTIONS[random.nextInt(ACTIONS.size)]
        var message = MESSAGES[random.nextInt(MESSAGES.size)]
        val to_user = generateCPF(random)
        val id = generateRandomId()
        val fileName = FILE_NAMES[random.nextInt(FILE_NAMES.size)]
        val content = CONTENTS[random.nextInt(CONTENTS.size)]
        val name = NAMES[random.nextInt(NAMES.size)]
        val amount = "$" + (random.nextInt(99901) + 10) * 100 + ",00"
        val date = generateDate(random)
        val hours = generateHours(random)

        message = message.replace("{CUSTOMER_ID}", id)
            .replace("{TIME}", hours)
            .replace("{DATE}", date)
            .replace("{AMOUNT}", amount)
            .replace("{USER_NAME}", name)
            .replace("{SENDER_NAME}", name)
            .replace("{CUSTOMER_NAME}", name)
            .replace("{CPF}", cpf)
            .replace("{CONTENT}", content)
            .replace("{FILE_NAME}", fileName)
            .replace("{ID}", id)
            .replace("{TO_USER}", to_user)
            .replace("{ACTION}", action)
            .replace("{DATETIME}", DATE_FORMAT.format(Date()))

        println(message)
        TimeUnit.SECONDS.sleep(1)
    }
  }
  fun main(args: Array<String>) {
    System.setOut(CustomPrintStream(System.out))
    setMessages()
    val random = Random()
    grammarPrepare(random)
    val numMessages = 500
    printMessages(random, numMessages)
  }
}
