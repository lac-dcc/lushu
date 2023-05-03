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

    fun run(numLogs: Int, sleepTime: Long = 0.toLong()) {
        for (i in 0..numLogs) {
            dispatchLogs(numLogs)
            if (sleepTime > 0) {
                TimeUnit.MILLISECONDS.sleep(sleepTime)
            }
        }
    }

    // sleepTime is milliseconds
    fun runInfinite(sleepTime: Long = 1000) {
        dispatchInfiniteLogs(sleepTime)
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

    private fun generateRandomCPF(): String {
        var cpf: String = ""
        for (i in 0..2) {
            for (j in 0..2) {
                cpf.plus(random.nextInt(10).toString())
            }
            if (i != 2) {
                cpf.plus(".")
            }
        }
        cpf.plus(String.format("-%d%d", random.nextInt(10), random.nextInt(10)))

        return cpf
    }

    private fun generateRandomId(): String {
        val validChars: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        var id = ""
        for (i in 0..7) {
            id.plus(validChars.get(random.nextInt(validChars.length)))
        }
        return id
    }

    private fun generateRandomTextWCPF(): String {
        val letters = ('a'..'z').toList() + ('A'..'Z').toList()

        val minChar = 10
        val maxChar = 180
        val numChar = random.nextInt(minChar, maxChar)

        val CPFprobability = 0.05 // 5% probability of generating " {CPF} "

        val randomString = (1..numChar).map {
            when {
                random.nextFloat() < CPFprobability -> " {CPF} "
                else -> letters.random().toString()
            }
        }.joinToString("")
        return randomString
    }

    private fun generateRandomIP(): String {
        val ip = "${random.nextInt(256)}"
        for (i in 0..3)
            ip.plus(".${random.nextInt(256)}")
        return ip
    }

    private fun generateRandomAmount(): String {
        return "\$${(random.nextInt(99901) + 10) * 100},00"
    }

    private fun logExamplesFpath(relativePath: String): String {
        return Paths.get(logExamplesDir, relativePath).toAbsolutePath().toString()
    }

    private fun dispatchLogs(numLogs: Int, timeout: Long = 1000, sensitiveMark: Boolean = false) {
        for (i in 0..numLogs) {
            val fileName = getRandomLine(logExamplesFpath("files.txt"))
            val content = getRandomLine(logExamplesFpath("contents.txt"))
            val name = getRandomLine(logExamplesFpath("names.txt"))
            val action = getRandomLine(logExamplesFpath("action.txt"))
            var message = getRandomLine(logExamplesFpath("messages.txt"))

            val cpf = if (sensitiveMark) "<s>${generateRandomCPF()}</s>" else generateRandomCPF()
            val to_user = if (sensitiveMark) "<s>${generateRandomCPF()}</s>" else generateRandomCPF()
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

            println(message)
        }
    }

    private fun dispatchInfiniteLogs(sleepTime: Long = 1000, sensitiveMark: Boolean = false) {
        for (i in 1..500) {
            var message = "{IP}: {ALPHAandCPF}"
            val ip = if (sensitiveMark) "<s>${generateRandomIP()}</s>" else generateRandomIP()
            var alphaAndCPF = generateRandomTextWCPF()

            alphaAndCPF = alphaAndCPF
                .replace("{CPF}", if (sensitiveMark) "<s>${generateRandomCPF()}</s>" else generateRandomCPF())

            message = message
                .replace("{IP}", ip)
                .replace("{ALPHAandCPF}", alphaAndCPF)

            System.out.println(message)
            TimeUnit.MILLISECONDS.sleep(sleepTime)
        }
    }
}
