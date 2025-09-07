package eu.codlab.cex

import eu.codlab.files.VirtualFile

class Config {
    private lateinit var internalConfig: Map<String, String>

    suspend fun load() {
        val home = System.getProperty("user.home")
        val file = VirtualFile(home, ".cex")

        if (!file.exists()) {
            internalConfig = mutableMapOf()
            return
        }

        val content = file.readString()
        println(file.absolutePath)

        internalConfig = content.split("\n").associate { line ->
            line.split("=").let {
                it[0] to (it.getOrNull(1) ?: "")
            }
        }
    }

    fun get(name: String) = internalConfig[name]
}
