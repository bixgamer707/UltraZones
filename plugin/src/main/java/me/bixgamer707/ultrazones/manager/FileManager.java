package me.bixgamer707.ultrazones.manager;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.file.File;
import me.bixgamer707.ultrazones.utils.Text;

public class FileManager {

    private final UltraZones plugin;
    public FileManager(UltraZones plugin) {
        this.plugin = plugin;
    }

    private File config,messages_ES,messages_EN,saves;
    private StringBuilder langFolder;
    
    public void registerFiles() {
        langFolder = new StringBuilder(plugin.getDataFolder().getAbsolutePath()).append("/lang/");
        config = new File(plugin,"config.yml");
        messages_ES = new File(plugin, "messages_ES.yml",new java.io.File(langFolder.toString()));
        messages_EN = new File(plugin, "messages_EN.yml", new java.io.File(langFolder.toString()));
        saves = new File(plugin, "saves.yml");
        plugin.getLogger().info(
                Text.hexColors("&aFiles registered...")
        );
    }

    public File getSaves() {
        return saves;
    }

    public File getMessages_ES() {
        return messages_ES;
    }

    public File getMessages_EN() {
        return messages_EN;
    }

    public File getConfig() {
        return config;
    }

    public StringBuilder getLangFolder() {
        return langFolder;
    }
}