// import lushu.Merger.Config.Config
// import lushu.Merger.Lattice.MergerLattice
// import lushu.Merger.Lattice.Node.Node
// import lushu.Merger.Lattice.NodeFactory
// import lushu.Merger.Lattice.NodePrinter
// import lushu.Merger.Merger.Merger

// class MergerInterface(
//     private var merger: Merger,
//     private var nodeFactory: NodeFactory
// ) {

//     init {
//         val args = ArrayList<String>()
//         args.add("./text_files/config.yaml")
//         val config = Config.fromCLIArgs(args)
//         nodeFactory = config.nodeFactory
//         merger = Merger(MergerLattice(nodeFactory))
//     }

//     fun getNodeFactory(): NodeFactory {
//         return nodeFactory
//     }

//     fun getMerger(): Merger {
//         return merger
//     }

//     fun getTokenRegex(token: Node): String {
//         return NodePrinter.print(token)
//     }

//     fun matchToken(token: Node, str: String): Boolean {
//         val pattern = Pattern.compile(getTokenRegex(token))
//         return pattern.matcher(str).matches()
//     }

//     fun isTop(token: Node): Boolean {
//         return nodeFactory.isTop(token)
//     }

//     fun printTokens(tokenList: List<Node>): String {
//         return NodePrinter.print(tokenList)
//     }
// }
