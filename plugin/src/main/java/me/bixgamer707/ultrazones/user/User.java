package me.bixgamer707.ultrazones.user;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.enums.Color;
import me.bixgamer707.ultrazones.file.File;
import me.bixgamer707.ultrazones.utils.Text;
import me.bixgamer707.ultrazones.wgevents.events.RegionEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionLeftEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsLeftEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.UUID;

public class User extends UserData{
    public User(UUID uuid, StringBuilder name) {
        super(uuid, name);
    }

    @Override
    public void onRegionJoin(RegionEnteredEvent event) {
        if(event.getPlayer()==null)return;

        File config = UltraZones.getInstance().getFileManager().getConfig();
        if(!config.contains("Zones"))return;

        for(String zone : config.getConfigurationSection("Zones").getKeys(false)){
            if(!config.contains("Zones."+zone+".join"))continue;

            if(config.contains("Zones."+zone+".join.execute-commands")){
                for(String cmd : config.getStringList("Zones."+zone+".join.execute-commands")){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%",getName().toString()));
                }
            }
            if(config.contains("Zones."+zone+".join.sounds")){
                if(config.contains("Zones."+zone+".join.sounds.to-server")){
                    for(String sounds : config.getStringList("Zones."+zone+".join.sounds.to-server")){
                        String[] split = sounds.split(":");
                        try {
                            Sound sound = Sound.valueOf(split[0]);
                            int vol = Integer.parseInt(split[1]);
                            float pitch = Float.parseFloat(split[2]);

                            Bukkit.getOnlinePlayers().forEach(player ->
                                    player.playSound(player.getLocation(),sound,vol,pitch)
                            );
                        }catch (IllegalArgumentException e){
                            Text.sendMsgConsole(
                                    Color.RED+"THE SOUND "+ split[0] + "IS INCOMPATIBLE."
                            );
                        }
                    }
                }
                if(config.contains("Zones."+zone+".join.sounds.player")){
                    for(String sounds : config.getStringList("Zones."+zone+".join.sounds.player")){
                        String[] split = sounds.split(":");
                        try {
                            Sound sound = Sound.valueOf(split[0]);
                            int vol = Integer.parseInt(split[1]);
                            float pitch = Float.parseFloat(split[2]);

                            event.getPlayer().playSound(event.getPlayer().getLocation(),sound,vol,pitch);
                        }catch (IllegalArgumentException e){
                            Text.sendMsgConsole(
                                    Color.RED+"SOUND "+ split[0] + "IS INCOMPATIBLE."
                            );
                        }
                    }
                }
            }
            if(config.contains("Zones."+zone+".join.messages")){
                if(config.contains("Zones."+zone+".join.messages.action-bar") &&
                        config.getBoolean("Zones."+zone+".join.messages.action-bar.enable")){
                    Text.sendActionBar(event.getPlayer(), config.getString("Zones."+zone+".join.messages.action-bar.message"));
                }
                if(config.contains("Zones."+zone+".join.messages.message")){
                    event.getPlayer().sendMessage(Text.sanitizeString(event.getPlayer(),
                            config.getString("Zones."+zone+".join.messages.message")));
                }
            }
            if(config.contains("Zones."+zone+".join.permission-requirement")){
                if(config.contains("Zones."+zone+".join.permission-requirement.value") &&
                        config.getBoolean("Zones."+zone+".join.permission-requirement.enable")){
                    if(!event.getPlayer().hasPermission(config.getString("Zones."+zone+".join.permission-requirement.value"))){
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(Text.hexColors(config.getString("Zones."+zone+".join.permission-requirement.message-no-available")));
                    }
                }
            }
        }
    }

    @Override
    public void onRegionsJoin(RegionsEnteredEvent event) {

    }

    @Override
    public void onRegionLeft(RegionLeftEvent event) {
        if(event.getPlayer()==null)return;

        File config = UltraZones.getInstance().getFileManager().getConfig();
        if(!config.contains("Zones"))return;

        for(String zone : config.getConfigurationSection("Zones").getKeys(false)){
            if(!config.contains("Zones."+zone+".left"))continue;

            if(config.contains("Zones."+zone+".left.execute-commands")){
                for(String cmd : config.getStringList("Zones."+zone+".left.execute-commands")){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%",getName().toString()));
                }
            }
            if(config.contains("Zones."+zone+".left.sounds")){
                if(config.contains("Zones."+zone+".left.sounds.to-server")){
                    for(String sounds : config.getStringList("Zones."+zone+".left.sounds.to-server")){
                        String[] split = sounds.split(":");
                        try {
                            Sound sound = Sound.valueOf(split[0]);
                            int vol = Integer.parseInt(split[1]);
                            float pitch = Float.parseFloat(split[2]);

                            Bukkit.getOnlinePlayers().forEach(player ->
                                    player.playSound(player.getLocation(),sound,vol,pitch)
                            );
                        }catch (IllegalArgumentException e){
                            Text.sendMsgConsole(
                                    Color.RED+"THE SOUND "+ split[0] + "IS INCOMPATIBLE."
                            );
                        }
                    }
                }
                if(config.contains("Zones."+zone+".left.sounds.player")){
                    for(String sounds : config.getStringList("Zones."+zone+".left.sounds.player")){
                        String[] split = sounds.split(":");
                        try {
                            Sound sound = Sound.valueOf(split[0]);
                            int vol = Integer.parseInt(split[1]);
                            float pitch = Float.parseFloat(split[2]);

                            event.getPlayer().playSound(event.getPlayer().getLocation(),sound,vol,pitch);
                        }catch (IllegalArgumentException e){
                            Text.sendMsgConsole(
                                    Color.RED+"THE SOUND "+ split[0] + "IS INCOMPATIBLE."
                            );
                        }
                    }
                }
            }
            if(config.contains("Zones."+zone+".left.messages")){
                if(config.contains("Zones."+zone+".left.messages.action-bar") &&
                        config.getBoolean("Zones."+zone+".left.messages.action-bar.enable")){
                    Text.sendActionBar(event.getPlayer(), config.getString("Zones."+zone+".left.messages.action-bar.message"));
                }
                if(config.contains("Zones."+zone+".left.messages.message")){
                    event.getPlayer().sendMessage(Text.sanitizeString(event.getPlayer(),
                            config.getString("Zones."+zone+".left.messages.message")));
                }
            }
            if(config.contains("Zones."+zone+".left.permission-requirement")){
                if(config.contains("Zones."+zone+".left.permission-requirement.value") &&
                        config.getBoolean("Zones."+zone+".left.permission-requirement.enable")){
                    if(!event.getPlayer().hasPermission(config.getString("Zones."+zone+".left.permission-requirement.value"))){
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(Text.hexColors(config.getString("Zones."+zone+".left .permission-requirement.message-no-available")));
                    }
                }
            }
        }
    }

    @Override
    public void onRegionsLeft(RegionsLeftEvent event) {

    }
}
