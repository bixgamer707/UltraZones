package me.bixgamer707.ultrazones.manager;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.file.File;
import me.bixgamer707.ultrazones.utils.Text;

public class FileManager {

    private final UltraZones plugin;
    public FileManager(UltraZones plugin) {
        this.plugin = plugin;
    }

    private File config,saves,breeds;
    
    public void registerFiles() {
        config = new File(plugin,"config.yml");
        if(plugin.getServer().getPluginManager().getPlugin("UltraBreeds") != null){
            breeds = new File(plugin, "breeds.yml");
        }
        saves = new File(plugin, "saves.yml");
        plugin.getLogger().info(
                Text.hexColors("&aFiles registered...")
        );
    }

    public File getSaves() {
        return saves;
    }

    public File getConfig() {
        return config;
    }

    public File getBreeds() {
        return breeds;
    }
}