package lushu.Lushu.LogGenerator

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

    init {
        // Train grammar by dispatching a few logs
        dispatchLogs(100, true)
    }

    fun run(numLogs: Int) {
        for (i in 0..numLogs) {
            dispatchLogs(numLogs)
            TimeUnit.SECONDS.sleep(1)
        }
    }

    private fun getRandomLine(file: String): String {
        return File(file).readLines().random()
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
        val cpf: String = ""
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
        val validChars: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val sb: StringBuilder = StringBuilder()
        for (i in 0..7) {
            sb.append(validChars.charAt(random.nextInt(validChars.length())))
        }
        return sb.toString()
    }

    private fun generateRandomAmount(): String {
        return "\$${(random.nextInt(99901) + 10) * 100},00"
    }

    private fun logExamplesFpath(relativePath: String): String {
        return Paths.get(logExamplesDir, relativePath).toAbsolutePath().toString()
    }

    private fun dispatchLogs(numLogs: Int, sensitiveMark: Boolean = false) {
        for (i in 0..numLogs) {
            val fileName = getRandomLine(logExamplesFpath("files.txt"))
            val content = getRandomLine(logExamplesFpath("contents.txt"))
            val name = getRandomLine(logExamplesFpath("names.txt"))
            val action = getRandomLine(logExamplesFpath("action.txt"))
            val message = getRandomLine(logExamplesFpath("messages.txt"))

            val cpf = if (sensitiveMark) "<s>${generateCPF()}</s>" else generateCPF()
            val to_user = if (sensitiveMark) "<s>${generateCPF()}</s>" else generateCPF()
            val id = generateRandomId()
            val amount = generateRandomAmount()
            val date = generateDate()
            val hours = generateHours()

            message = message
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

            System.out.println(message)
        }
    }
}
