fun main(args: Array<String>) {
    System.setOut(CustomPrintStream(System.out))
    val lg = LogGenerator()
    lg.run(500)
}
