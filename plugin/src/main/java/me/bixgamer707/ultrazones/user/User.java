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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User extends UserData{
    public User(UUID uuid, String name) {
        super(uuid, name);
    }

    @Override
    public void onRegionJoin(RegionEnteredEvent event) {
        if(event.getPlayer()==null)return;

        File config = UltraZones.getInstance().getFileManager().getConfig();
        if(!config.contains("Zones"))return;


        String zone = event.getRegionName();
        if(!config.contains("Zones."+zone+".join"))return;

        if(!getZonesJoined().contains(zone)){
            if(!config.contains("Zones."+zone+".join.first-join"))return;

            addZone(zone);
            if(config.contains("Zones."+zone+".join.first-join.execute-commands")){
                for(String cmd : config.getStringList("Zones."+zone+".join.first-join.execute-commands")){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%",getName().toString()));
                }
            }
            if(config.contains("Zones."+zone+".join.first-join.sounds")){
                if(config.contains("Zones."+zone+".join.first-join.sounds.to-server")){
                    for(String sounds : config.getStringList("Zones."+zone+".join.first-join.sounds.to-server")){
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
                if(config.contains("Zones."+zone+".join.first-join.sounds.player")){
                    for(String sounds : config.getStringList("Zones."+zone+".join.first-join.sounds.player")){
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
            if(config.contains("Zones."+zone+".join.first-join.messages")){
                if(config.contains("Zones."+zone+".join.first-join.messages.action-bar") &&
                        config.getBoolean("Zones."+zone+".join.first-join.messages.action-bar.enable")){
                    Text.sendActionBar(event.getPlayer(), config.getString("Zones."+zone+".join.first-join.messages.action-bar.message"));
                }
                if(config.contains("Zones."+zone+".join.first-join.messages.message")){
                    event.getPlayer().sendMessage(Text.sanitizeString(event.getPlayer(),
                            config.getString("Zones."+zone+".join.first-join.messages.message")));
                }
            }
            return;
        }

        if(config.contains("Zones."+zone+".join.permission-requirement")){
            if(config.contains("Zones."+zone+".join.permission-requirement.value") &&
                    config.getBoolean("Zones."+zone+".join.permission-requirement.enable")){
                if(!event.getPlayer().hasPermission(config.getString("Zones."+zone+".join.permission-requirement.value"))){
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(Text.hexColors(config.getString("Zones."+zone+".join.permission-requirement.message-no-available")));
                    return;
                }
            }
        }



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
    }

    @Override
    public void onRegionsJoin(RegionsEnteredEvent event) {

    }

    @Override
    public void onRegionLeft(RegionLeftEvent event) {
        if(event.getPlayer()==null)return;

        File config = UltraZones.getInstance().getFileManager().getConfig();
        if(!config.contains("Zones"))return;

        String zone = event.getRegionName();

        if(!config.contains("Zones."+zone+".left"))return;

        if(config.contains("Zones."+zone+".left.permission-requirement")){
            if(config.contains("Zones."+zone+".left.permission-requirement.value") &&
                    config.getBoolean("Zones."+zone+".left.permission-requirement.enable")){
                if(!event.getPlayer().hasPermission(config.getString("Zones."+zone+".left.permission-requirement.value"))){
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(Text.hexColors(config.getString("Zones."+zone+".left .permission-requirement.message-no-available")));
                    return;
                }
            }
        }

        if(!getZonesExit().contains(zone)){
            if(!config.contains("Zones."+zone+".left.first-left"))return;

            addZoneExit(zone);
            if(config.contains("Zones."+zone+".left.first-left.execute-commands")){
                for(String cmd : config.getStringList("Zones."+zone+".left.first-left.execute-commands")){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%",getName().toString()));
                }
            }
            if(config.contains("Zones."+zone+".left.first-left.sounds")){
                if(config.contains("Zones."+zone+".left.first-left.sounds.to-server")){
                    for(String sounds : config.getStringList("Zones."+zone+".left.first-left.sounds.to-server")){
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
                if(config.contains("Zones."+zone+".left.first-left.sounds.player")){
                    for(String sounds : config.getStringList("Zones."+zone+".left.first-left.sounds.player")){
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
            if(config.contains("Zones."+zone+".left.first-left.messages")){
                if(config.contains("Zones."+zone+".left.first-left.messages.action-bar") &&
                        config.getBoolean("Zones."+zone+".left.first-left.messages.action-bar.enable")){
                    Text.sendActionBar(event.getPlayer(), config.getString("Zones."+zone+".left.first-left.messages.action-bar.message"));
                }
                if(config.contains("Zones."+zone+".left.first-left.messages.message")){
                    event.getPlayer().sendMessage(Text.sanitizeString(event.getPlayer(),
                            config.getString("Zones."+zone+".left.first-left.messages.message")));
                }
            }
            return;
        }

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
    }

    @Override
    public void onRegionsLeft(RegionsLeftEvent event) {

    }

    @Override
    public void loadData() {
        File saves = UltraZones.getInstance().getFileManager().getSaves();
        if(!saves.contains("Saves."+uuid)){
            return;
        }

        List<String> zoneJoined = saves.getStringList("Saves." + uuid + ".zonesJoined");
        List<String> zoneExit = saves.getStringList("Saves." + uuid + ".zonesExit");
        setZonesJoined(zoneJoined);
        setZonesExit(zoneExit);
    }

    @Override
    public void saveData() {
        File saves = UltraZones.getInstance().getFileManager().getSaves();
        saves.set("Saves." + uuid + ".zonesJoined", zonesJoined);
        saves.set("Saves." + uuid + ".zonesExit", zonesExit);
        saves.save();
    }
}
