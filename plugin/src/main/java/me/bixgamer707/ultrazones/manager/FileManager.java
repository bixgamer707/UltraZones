package me.bixgamer707.ultrazones.manager;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.file.File;
import me.bixgamer707.ultrazones.utils.Text;

public class FileManager implements LoaderManager{

    private final UltraZones plugin;
    public FileManager(UltraZones plugin) {
        this.plugin = plugin;
    }

    private File config,breeds,saves;

    @Override
    public void start() {
        config = new File(plugin,"config.yml");
        saves = new File(plugin, "saves.yml");
        if(plugin.getServer().getPluginManager().getPlugin("UltraBreeds") != null){
            breeds = new File(plugin, "breeds.yml");
            plugin.getLogger().info(
                    Text.hexColors("&aUltraBreeds &6has been hooked")
            );
        }
        plugin.getLogger().info(
                Text.hexColors("&aFiles registered...")
        );
    }

    @Override
    public void stop() {
        config.save();
        saves.save();
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