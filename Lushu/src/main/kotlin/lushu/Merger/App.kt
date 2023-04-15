package lushu.Merger

import lushu.Merger.Config.Config
import lushu.Merger.Lattice.MergerLattice
import lushu.Merger.Lattice.NodePrinter
import lushu.Merger.Merger.Merger
import lushu.Merger.Merger.Token

fun mergeWordsFromStdin(cfg: Config): String {
    val nf = cfg.nodeFactory
    val merger = Merger(MergerLattice(nf))
    val input = readLine()
    if (input == null || input.isEmpty()) {
        return ""
    }
    val words = input.split(" ")
    if (words.isEmpty()) {
        return ""
    }
    var acc: List<Token> = nf.buildIntervalNodes(words[0])
    words.forEach {
        acc = merger.merge(acc, nf.buildIntervalNodes(it))
    }
    return NodePrinter.print(acc)
}

fun main(args: Array<String>) {
    if (args.size < 1) {
        println(
            "Usage: echo 'word1 word2 ...' | <this-program> <base-nodes-file>"
        )
        return
    }
    val config = Config.fromCLIArgs(args.toList())
    println(mergeWordsFromStdin(config))
}
