package me.bixgamer707.ultrazones.placeholders;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.file.File;
import me.bixgamer707.ultrazones.user.User;
import me.bixgamer707.ultrazones.utils.Text;
import me.bixgamer707.ultrazones.wgevents.WorldGuardChecks;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

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
            User user = plugin.getUsersManager().getUserByUuid(player.getUniqueId());
            if(user == null) return "";

            File config = plugin.getFileManager().getConfig();
            if(!config.contains("Zones"))

            for(String idRegion : WorldGuardChecks.getRegionsNames(player.getUniqueId())){
                if(WorldGuardChecks.getRegionsNames(player.getUniqueId()).isEmpty()){
                    return Text.hexColors(config.getString("Player-no-region"));
                }
                for(String key : config.getConfigurationSection("Zones").
                        getKeys(false)){
                    StringBuilder path = new StringBuilder("Zones.").
                            append(key);
                    if(!config.getBoolean(path.append(".placeholder.enable")
                            .toString()))continue;

                    if(!idRegion.equals(key))continue;

                    return Text.sanitizeString(player, config.getString(path.append(".placeholder.replacer").toString()));
                }

//List one: spawn, shop
                //List two: shop, spawn
            }
            return "";
        }
        return null;
    }
}
