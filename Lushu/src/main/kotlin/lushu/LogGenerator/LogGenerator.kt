package lushu.LogGenerator

import java.io.File
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Random
import java.util.concurrent.TimeUnit

class LogGenerator(
    private val logExamplesDir: String
) {
    private val random = Random()
    private val datefmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private val files = File(logExamplesFpath("files.txt")).readLines()
    private val contents = File(logExamplesFpath("contents.txt")).readLines()
    private val names = File(logExamplesFpath("names.txt")).readLines()
    private val actions = File(logExamplesFpath("actions.txt")).readLines()
    private val messages = File(logExamplesFpath("messages.txt")).readLines()

    fun run(numLogs: Int, sleepTime: Long = 0.toLong()) {
        for (i in 0 until numLogs) {
            dispatchLog()
            if (sleepTime > 0) {
                TimeUnit.MILLISECONDS.sleep(sleepTime)
            }
        }
    }

    private fun generateDate(): String {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = random.nextInt(2) + 2023
        val month: Int = random.nextInt(12)
        val day: Int = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + 1
        calendar.set(year, month, day)
        return SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime())
    }

    private fun generateHours(): String {
        return String.format("%02d:%02d", random.nextInt(24), random.nextInt(60))
    }

    private fun generateCPF(): String {
        var cpf: String = ""
        for (i in 0..2) {
            for (j in 0..2) {
                cpf += random.nextInt(10).toString()
            }
            if (i != 2) {
                cpf += "."
            }
        }
        cpf += String.format("-%d%d", random.nextInt(10), random.nextInt(10))
        return cpf
    }

    private fun generateRandomId(): String {
        val validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        var s = ""
        for (i in 0..7) {
            s += validChars[random.nextInt(validChars.length)]
        }
        return s
    }

    private fun generateRandomAmount(): String {
        return "\$${(random.nextInt(99901) + 10) * 100},00"
    }

    private fun logExamplesFpath(relativePath: String): String {
        return Paths.get(logExamplesDir, relativePath).toAbsolutePath().toString()
    }

    private fun dispatchLog() {
        val fileName = files.random()
        val content = contents.random()
        val name = names.random()
        val action = actions.random()

        val cpf = generateCPF()
        val to_user = generateCPF()
        val id = generateRandomId()
        val amount = generateRandomAmount()
        val date = generateDate()
        val hours = generateHours()

        val message = messages.random()
            .replace("{CUSTOMER_ID}", id)
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
            .replace("{DATETIME}", datefmt.format(Date()))

        println(message)
    }
}
