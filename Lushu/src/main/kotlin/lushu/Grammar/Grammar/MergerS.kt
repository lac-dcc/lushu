package lushu.Grammar.Grammar

import lushu.Merger.Merger.Merger

// MergerS is a singleton of lushu.Merger.Merger.Merger
class MergerS private constructor() {
    companion object {
        private var instance: Merger? = null

        fun load(configFilePath: String) {
            instance = Merger.fromConfigFile(configFilePath)
        }

        fun merger(): Merger {
            if (instance == null) {
                throw Exception(
                    "Unable to get instance: Merger has not been " +
                        "initialized"
                )
            }
            return instance!!
        }
    }
}
