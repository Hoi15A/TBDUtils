package computer.austins.tbdutils.util.config

import computer.austins.tbdutils.plugin

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration

import java.io.File

object ConfigManager {
    private lateinit var quotesFile : File
    private lateinit var quotesConfig : FileConfiguration

    private lateinit var eventFile : File
    private lateinit var eventConfig : FileConfiguration

    fun setup() {
        plugin.config.options().copyDefaults()
        plugin.saveDefaultConfig()
        setupQuotesConfig()
        setupEventConfig()
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

    private fun setupEventConfig() {
        eventFile = File(Bukkit.getServer().pluginManager.getPlugin("TBDUtils")!!.dataFolder, "event.yml")
        generateConfigIfNull(eventFile)
        eventConfig = YamlConfiguration.loadConfiguration(eventFile)
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
            TBDConfig.EVENT -> {
                eventConfig = YamlConfiguration.loadConfiguration(eventFile)
            }
        }
    }

    fun getFile(config : TBDConfig) : File {
        return when(config) {
            TBDConfig.QUOTES -> quotesFile
            TBDConfig.EVENT -> eventFile
        }
    }

    fun getConfig(config : TBDConfig) : FileConfiguration {
        return when(config) {
            TBDConfig.QUOTES -> quotesConfig
            TBDConfig.EVENT -> eventConfig
        }
    }
}

enum class TBDConfig(val displayName : String, val configFile : String) {
    QUOTES("Quotes", "quotes.yml"),
    EVENT("Event", "event.yml")
}