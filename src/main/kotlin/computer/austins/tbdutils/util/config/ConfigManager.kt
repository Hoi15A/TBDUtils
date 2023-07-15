package computer.austins.tbdutils.util.config

import computer.austins.tbdutils.plugin

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration

import java.io.File

object ConfigManager {
    private lateinit var quotesFile : File
    private lateinit var quotesConfig : FileConfiguration

    fun setup() {
        plugin.config.options().copyDefaults()
        plugin.saveDefaultConfig()
        setupQuotesConfig()
    }

    private fun setupQuotesConfig() {
        quotesFile = File(Bukkit.getServer().pluginManager.getPlugin("TBDUtils")!!.dataFolder, "quotes.yml")
        generateConfigIfNull(quotesFile)
        quotesConfig = YamlConfiguration.loadConfiguration(quotesFile)
    }

    private fun generateConfigIfNull(file : File) {
        if(!file.exists()) {
            try {
                file.createNewFile()
            } catch(e : Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveConfig(file : File, fileConfiguration : FileConfiguration) {
        try {
            fileConfiguration.save(file)
        } catch(e : java.lang.Exception) {
            plugin.logger.severe("Unable to save a configuration:\n${e.printStackTrace()}")
        }
    }

    fun reloadConfig(config : TBDConfig) {
        when(config) {
            TBDConfig.QUOTES -> {
                quotesConfig = YamlConfiguration.loadConfiguration(quotesFile)
            }
        }
    }

    fun getQuotesConfig() : FileConfiguration {
        return quotesConfig
    }
}

enum class TBDConfig(val displayName : String, val configFile : String) {
    QUOTES("Quotes", "quotes.yml")
}