package me.bixgamer707.ultrazones.placeholders;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.file.File;
import me.bixgamer707.ultrazones.user.User;
import me.bixgamer707.ultrazones.utils.Text;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class Placeholders extends PlaceholderExpansion {
    private final UltraZones plugin;
    public Placeholders(UltraZones plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }
    @Override
    public boolean canRegister() {
        return true;
    }
    @Override
    public String getAuthor() {
        return "bixgamer707";
    }
    @Override
    public String getIdentifier() {
        return "ultrazones";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }


    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (player == null) {
            return "";
        }

        if (identifier.equals("zone_player")) {
            CompletableFuture<User> user = plugin.getUsersManager().getUserByUuid(
                    player.getUniqueId(),
                    player.getName()
            );
            User userData = user.join();

            return Text.hexColors(userData.getCurrentZone());
        }
        if(identifier.equals("total_zones")) {
            File config = plugin.getFileManager().getConfig();
            if(!config.contains("Zones"))return "Nothing zones registered";

            for(String key : config.getConfigurationSection("Zones").getKeys(false)){
                return key;
            }
            return "ERROR";
        }
        if(identifier.equals("total_zones_player")) {
            CompletableFuture<User> user = plugin.getUsersManager().getUserByUuid(
                    player.getUniqueId(),
                    player.getName()
            );

            for(String key : user.join().getZonesJoined()){
                return key;
            }
            return "ERROR";
        }
        return null;
    }
}
