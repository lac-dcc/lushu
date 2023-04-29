package lushu.Grammar

import lushu.Merger.Merger.Merger

// Merger is a singleton
class Merger private constructor() {
    companion object {
        private var instance: Merger? = null

        fun init(configFilePath: String) {
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
