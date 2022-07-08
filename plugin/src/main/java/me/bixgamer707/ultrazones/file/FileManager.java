package me.bixgamer707.ultrazones.file;

import me.bixgamer707.ultrazones.Main;
import me.bixgamer707.ultrazones.utils.Text;

public class FileManager {

    private final Main plugin;
    public FileManager(Main plugin) {
        this.plugin = plugin;
    }

    private FileCreator config,messages_ES,messages_EN,saves;

    public void registerFiles() {
        config = new FileCreator(plugin,"config.yml");
        messages_ES = new FileCreator(plugin, "messages_ES.yml");
        messages_EN = new FileCreator(plugin, "messages_EN.yml");
        saves = new FileCreator(plugin, "saves.yml");
        plugin.getLogger().info(
                Text.hexColors("&aFiles registered...")
        );
    }

    public FileCreator getSaves() {
        return saves;
    }

    public FileCreator getMessages_ES() {
        return messages_ES;
    }

    public FileCreator getMessages_EN() {
        return messages_EN;
    }

    public FileCreator getConfig() {
        return config;
    }
}